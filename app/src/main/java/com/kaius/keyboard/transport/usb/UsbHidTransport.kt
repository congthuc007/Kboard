package com.kaius.keyboard.transport.usb

import android.content.Context
import android.util.Base64
import com.kaius.keyboard.transport.ConnectionStatus
import com.kaius.keyboard.transport.InputTransport
import com.kaius.keyboard.transport.LogEntry
import com.kaius.keyboard.transport.TransportMode
import com.kaius.keyboard.transport.TransportState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class UsbHidTransport(
    private val context: Context,
    private val scope: CoroutineScope
) : InputTransport {

    private val usbExecutor = Executors.newSingleThreadExecutor()
    private val usbDispatcher = usbExecutor.asCoroutineDispatcher()
    private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    private var directOut: FileOutputStream? = null
    private var rootWriterProcess: Process? = null
    private var rootWriterStdin: BufferedWriter? = null
    private var useOctalEscape = false

    private var createdSymlinkPath: String? = null
    private var activeGadgetPath: String? = null
    private var activeUdcName: String? = null
    private var isInitialized = false

    private val _state = MutableStateFlow(
        TransportState(
            mode = TransportMode.USB_HID,
            status = ConnectionStatus.DISCONNECTED,
            statusMessage = "Chưa bật USB HID (Cần Root)"
        )
    )
    override val state: StateFlow<TransportState> = _state.asStateFlow()

    companion object {
        private const val HID_DEVICE_PATH = "/dev/hidg0"

        // Standard 63-byte USB Boot Keyboard HID Report Descriptor (no Report ID, 8-byte report)
        val USB_KEYBOARD_REPORT_DESCRIPTOR = byteArrayOf(
            0x05.toByte(), 0x01.toByte(), // Usage Page (Generic Desktop)
            0x09.toByte(), 0x06.toByte(), // Usage (Keyboard)
            0xA1.toByte(), 0x01.toByte(), // Collection (Application)
            0x05.toByte(), 0x07.toByte(), //   Usage Page (Key Codes)
            0x19.toByte(), 0xE0.toByte(), //   Usage Minimum (Left Ctrl)
            0x29.toByte(), 0xE7.toByte(), //   Usage Maximum (Right GUI)
            0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
            0x25.toByte(), 0x01.toByte(), //   Logical Maximum (1)
            0x75.toByte(), 0x01.toByte(), //   Report Size (1)
            0x95.toByte(), 0x08.toByte(), //   Report Count (8)
            0x81.toByte(), 0x02.toByte(), //   Input (Data, Var, Abs) - Modifiers
            0x95.toByte(), 0x01.toByte(), //   Report Count (1)
            0x75.toByte(), 0x08.toByte(), //   Report Size (8)
            0x81.toByte(), 0x01.toByte(), //   Input (Cnst) - Reserved byte
            0x95.toByte(), 0x05.toByte(), //   Report Count (5)
            0x75.toByte(), 0x01.toByte(), //   Report Size (1)
            0x05.toByte(), 0x08.toByte(), //   Usage Page (LEDs)
            0x19.toByte(), 0x01.toByte(), //   Usage Minimum (Num Lock)
            0x29.toByte(), 0x05.toByte(), //   Usage Maximum (Kana)
            0x91.toByte(), 0x02.toByte(), //   Output (Data, Var, Abs) - LEDs
            0x95.toByte(), 0x01.toByte(), //   Report Count (1)
            0x75.toByte(), 0x03.toByte(), //   Report Size (3)
            0x91.toByte(), 0x01.toByte(), //   Output (Cnst) - LED padding
            0x95.toByte(), 0x06.toByte(), //   Report Count (6)
            0x75.toByte(), 0x08.toByte(), //   Report Size (8)
            0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
            0x25.toByte(), 0x65.toByte(), //   Logical Maximum (101)
            0x05.toByte(), 0x07.toByte(), //   Usage Page (Key Codes)
            0x19.toByte(), 0x00.toByte(), //   Usage Minimum (0)
            0x29.toByte(), 0x65.toByte(), //   Usage Maximum (101)
            0x81.toByte(), 0x00.toByte(), //   Input (Data, Ary, Abs) - 6 keycodes
            0xC0.toByte()                 // End Collection
        )
    }

    private fun log(message: String, isError: Boolean = false) {
        val entry = LogEntry(
            timestamp = timeFormat.format(Date()),
            message = message,
            isError = isError
        )
        _state.update { curr ->
            val updatedLogs = (listOf(entry) + curr.logs).take(100)
            curr.copy(logs = updatedLogs)
        }
    }

    private data class ShellResult(val exitCode: Int, val output: String)

    private fun runSuCommand(script: String, timeoutSec: Long = 5): ShellResult {
        return try {
            val process = ProcessBuilder("su", "-c", script)
                .redirectErrorStream(true)
                .start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val sb = StringBuilder()
            val finished = process.waitFor(timeoutSec, TimeUnit.SECONDS)
            if (!finished) {
                process.destroyForcibly()
                return ShellResult(-1, "Timeout ($timeoutSec s)")
            }
            var line = reader.readLine()
            while (line != null) {
                if (sb.isNotEmpty()) sb.append('\n')
                sb.append(line)
                line = reader.readLine()
            }
            ShellResult(process.exitValue(), sb.toString().trim())
        } catch (e: Exception) {
            ShellResult(-1, e.message ?: "Không chạy được lệnh su")
        }
    }

    override fun initialize() {
        scope.launch(usbDispatcher) {
            closeWritersInternal()
            _state.update {
                it.copy(
                    status = ConnectionStatus.CONNECTING,
                    statusMessage = "Đang kiểm tra Root & USB HID..."
                )
            }
            log("Kiểm tra quyền Root (su)...")

            val idRes = runSuCommand("id")
            if (idRes.exitCode != 0 || !idRes.output.contains("uid=0")) {
                val reason = if (idRes.output.isNotEmpty()) idRes.output else "Thiết bị chưa Root hoặc bị từ chối quyền su"
                log("Không có quyền Root: $reason", isError = true)
                _state.update {
                    it.copy(
                        status = ConnectionStatus.FAILED,
                        statusMessage = "Cần Root (su) để dùng USB HID"
                    )
                }
                isInitialized = false
                return@launch
            }
            log("Đã xác nhận quyền Root (${idRes.output.lineSequence().firstOrNull() ?: "uid=0"})")

            // Step 1: Check if /dev/hidg0 already exists; if not, configure ConfigFS USB HID gadget
            val existsCheck = runSuCommand("test -e $HID_DEVICE_PATH && echo EXISTS || echo MISSING")
            if (!existsCheck.output.contains("EXISTS")) {
                log("Chưa có $HID_DEVICE_PATH, đang cấu hình USB HID Gadget (ConfigFS)...")
                val setupOk = setupConfigFsHidGadget()
                if (!setupOk) {
                    isInitialized = false
                    return@launch
                }
            } else {
                log("Phát hiện sẵn thiết bị $HID_DEVICE_PATH")
            }

            // Step 2: Grant permission and open writer (direct FileOutputStream first, persistent su fallback)
            runSuCommand("chmod 666 $HID_DEVICE_PATH")
            val openedDirect = tryOpenDirectWriter()
            if (openedDirect) {
                log("Đã mở trực tiếp $HID_DEVICE_PATH (Direct I/O)")
            } else {
                log("SELinux chặn mở trực tiếp, chuyển sang kênh Root Shell (su)...")
                val openedRoot = tryOpenPersistentRootWriter()
                if (!openedRoot) {
                    _state.update {
                        it.copy(
                            status = ConnectionStatus.FAILED,
                            statusMessage = "Không mở được $HID_DEVICE_PATH"
                        )
                    }
                    isInitialized = false
                    return@launch
                }
                log("Đã mở kênh ghi Root Shell tới $HID_DEVICE_PATH")
            }

            isInitialized = true
            _state.update {
                it.copy(
                    status = ConnectionStatus.CONNECTED,
                    statusMessage = "USB HID sẵn sàng ($HID_DEVICE_PATH)",
                    targetName = "USB PC Host",
                    targetAddress = HID_DEVICE_PATH
                )
            }
            // Send clean empty report on startup
            writeReportInternal(ByteArray(8))
        }
    }

    private fun setupConfigFsHidGadget(): Boolean {
        val descFile = File(context.cacheDir, "kboard_hid_desc.bin")
        try {
            FileOutputStream(descFile).use { it.write(USB_KEYBOARD_REPORT_DESCRIPTOR) }
        } catch (e: Exception) {
            log("Lỗi tạo file descriptor tạm: ${e.message}", isError = true)
            _state.update {
                it.copy(status = ConnectionStatus.FAILED, statusMessage = "Lỗi tạo HID descriptor")
            }
            return false
        }

        val descBase64 = Base64.encodeToString(USB_KEYBOARD_REPORT_DESCRIPTOR, Base64.NO_WRAP)
        val setupScript = """
            set -e
            GADGET=""
            if [ -d /config/usb_gadget/g1 ]; then
                GADGET="/config/usb_gadget/g1"
            else
                for d in /config/usb_gadget/*; do
                    if [ -d "${'$'}d" ]; then
                        GADGET="${'$'}d"
                        break
                    fi
                done
            fi
            if [ -z "${'$'}GADGET" ]; then
                echo "ERR:NO_CONFIGFS"
                exit 2
            fi
            if ! mkdir -p "${'$'}GADGET/functions/hid.usb0" 2>/dev/null; then
                echo "ERR:NO_HID_FUNCTION"
                exit 3
            fi
            echo 1 > "${'$'}GADGET/functions/hid.usb0/protocol"
            echo 1 > "${'$'}GADGET/functions/hid.usb0/subclass"
            echo 8 > "${'$'}GADGET/functions/hid.usb0/report_length"
            if [ -f "${descFile.absolutePath}" ]; then
                cat "${descFile.absolutePath}" > "${'$'}GADGET/functions/hid.usb0/report_desc"
            else
                echo "$descBase64" | base64 -d > "${'$'}GADGET/functions/hid.usb0/report_desc"
            fi
            CFG=""
            if [ -d "${'$'}GADGET/configs/b.1" ]; then
                CFG="${'$'}GADGET/configs/b.1"
            else
                for c in "${'$'}GADGET"/configs/*; do
                    if [ -d "${'$'}c" ]; then
                        CFG="${'$'}c"
                        break
                    fi
                done
            fi
            if [ -z "${'$'}CFG" ]; then
                echo "ERR:NO_CONFIG_DIR"
                exit 4
            fi
            UDC_NAME="${'$'}(cat "${'$'}GADGET/UDC" 2>/dev/null | tr -d '\n\r')"
            if [ -z "${'$'}UDC_NAME" ]; then
                UDC_NAME="${'$'}(getprop sys.usb.controller 2>/dev/null | tr -d '\n\r')"
            fi
            if [ -z "${'$'}UDC_NAME" ]; then
                UDC_NAME="${'$'}(ls /sys/class/udc 2>/dev/null | head -n 1 | tr -d '\n\r')"
            fi
            if [ -z "${'$'}UDC_NAME" ]; then
                echo "ERR:NO_UDC"
                exit 5
            fi
            CREATED_LINK=""
            if [ ! -e "${'$'}CFG/hid.usb0" ]; then
                echo "" > "${'$'}GADGET/UDC" 2>/dev/null || true
                ln -s "${'$'}GADGET/functions/hid.usb0" "${'$'}CFG/hid.usb0"
                CREATED_LINK="${'$'}CFG/hid.usb0"
                echo "${'$'}UDC_NAME" > "${'$'}GADGET/UDC"
            fi
            i=0
            while [ ${'$'}i -lt 10 ]; do
                if [ -e $HID_DEVICE_PATH ]; then
                    break
                fi
                sleep 0.1
                i=${'$'}((i + 1))
            done
            if [ ! -e $HID_DEVICE_PATH ]; then
                echo "ERR:NO_HIDG0_NODE"
                exit 6
            fi
            echo "OK|${'$'}GADGET|${'$'}UDC_NAME|${'$'}CREATED_LINK"
        """.trimIndent()

        val res = runSuCommand(setupScript, timeoutSec = 8)
        if (res.exitCode != 0 || !res.output.contains("OK|")) {
            val userMsg = when {
                res.output.contains("ERR:NO_CONFIGFS") -> "Kernel không hỗ trợ ConfigFS USB Gadget"
                res.output.contains("ERR:NO_HID_FUNCTION") -> "Kernel thiếu module USB HID Gadget (f_hid)"
                res.output.contains("ERR:NO_UDC") -> "Không tìm thấy bộ điều khiển USB (UDC)"
                res.output.contains("ERR:NO_HIDG0_NODE") -> "Không tạo được node /dev/hidg0"
                else -> "Lỗi kích hoạt USB HID Gadget"
            }
            log("$userMsg (${res.output})", isError = true)
            _state.update {
                it.copy(
                    status = ConnectionStatus.FAILED,
                    statusMessage = userMsg
                )
            }
            return false
        }

        val okLine = res.output.lineSequence().firstOrNull { it.startsWith("OK|") } ?: ""
        val parts = okLine.split("|")
        activeGadgetPath = parts.getOrNull(1)?.takeIf { it.isNotBlank() }
        activeUdcName = parts.getOrNull(2)?.takeIf { it.isNotBlank() }
        createdSymlinkPath = parts.getOrNull(3)?.takeIf { it.isNotBlank() }
        log("Đã gắn ConfigFS USB HID: gadget=$activeGadgetPath, udc=$activeUdcName")
        return true
    }

    private fun tryOpenDirectWriter(): Boolean {
        return try {
            directOut = FileOutputStream(HID_DEVICE_PATH)
            true
        } catch (e: Exception) {
            directOut = null
            log("Direct open thất bại: ${e.message}")
            false
        }
    }

    private fun tryOpenPersistentRootWriter(): Boolean {
        return try {
            // Probe whether toybox printf supports \xHH or octal \0NNN
            val probe = runSuCommand("test \$(printf '\\x04' | wc -c) -eq 1 && echo HEX || echo OCTAL")
            useOctalEscape = !probe.output.contains("HEX")

            val proc = ProcessBuilder("su")
                .redirectErrorStream(true)
                .start()
            val writer = BufferedWriter(OutputStreamWriter(proc.outputStream, Charsets.US_ASCII))
            val reader = BufferedReader(InputStreamReader(proc.inputStream))
            writer.write("exec 3>$HID_DEVICE_PATH && echo HID_OPEN_OK || echo HID_OPEN_ERR\n")
            writer.flush()

            val line = reader.readLine() ?: ""
            if (!line.contains("HID_OPEN_OK")) {
                log("Không mở được $HID_DEVICE_PATH trong root shell: $line", isError = true)
                proc.destroy()
                return false
            }

            rootWriterProcess = proc
            rootWriterStdin = writer
            true
        } catch (e: Exception) {
            log("Lỗi mở kênh Root Shell: ${e.message}", isError = true)
            closeWritersInternal()
            false
        }
    }

    private fun formatBytesForPrintf(report: ByteArray): String {
        val sb = StringBuilder(report.size * 5)
        for (b in report) {
            val v = b.toInt() and 0xFF
            if (useOctalEscape) {
                sb.append("\\0")
                sb.append(v.toString(8).padStart(3, '0'))
            } else {
                sb.append("\\x")
                sb.append(v.toString(16).padStart(2, '0'))
            }
        }
        return sb.toString()
    }

    private fun writeReportInternal(report: ByteArray) {
        if (!isInitialized) return
        val direct = directOut
        if (direct != null) {
            try {
                direct.write(report)
                direct.flush()
                if (_state.value.status != ConnectionStatus.CONNECTED) {
                    _state.update {
                        it.copy(
                            status = ConnectionStatus.CONNECTED,
                            statusMessage = "USB HID đang hoạt động ($HID_DEVICE_PATH)"
                        )
                    }
                }
                return
            } catch (e: Exception) {
                val msg = e.message ?: "Lỗi ghi HID"
                if (msg.contains("EAGAIN") || msg.contains("Try again") || msg.contains("shutdown", ignoreCase = true)) {
                    _state.update {
                        it.copy(
                            status = ConnectionStatus.REGISTERED,
                            statusMessage = "Chờ cắm cáp USB vào PC..."
                        )
                    }
                } else {
                    log("Lỗi ghi trực tiếp $HID_DEVICE_PATH: $msg", isError = true)
                }
                return
            }
        }

        val writer = rootWriterStdin
        val proc = rootWriterProcess
        if (writer != null && proc != null && proc.isAlive) {
            try {
                val escaped = formatBytesForPrintf(report)
                writer.write("printf '$escaped' >&3\n")
                writer.flush()
            } catch (e: Exception) {
                log("Lỗi ghi kênh Root USB HID: ${e.message}", isError = true)
                _state.update {
                    it.copy(
                        status = ConnectionStatus.FAILED,
                        statusMessage = "Mất kết nối kênh USB HID"
                    )
                }
            }
        }
    }

    override fun sendRawReport(modifiers: Byte, keyCodes: ByteArray) {
        val report = ByteArray(8)
        report[0] = modifiers
        report[1] = 0x00
        for (i in 0 until minOf(6, keyCodes.size)) {
            report[2 + i] = keyCodes[i]
        }
        scope.launch(usbDispatcher) {
            writeReportInternal(report)
        }
    }

    override fun sendKeyDown(keyCode: Byte, modifiers: Byte) {
        sendRawReport(modifiers, byteArrayOf(keyCode))
    }

    override fun sendKeyUp(keyCode: Byte, modifiers: Byte) {
        sendRawReport(modifiers, byteArrayOf())
    }

    override fun sendKeyTap(keyCode: Byte, modifiers: Byte) {
        scope.launch(usbDispatcher) {
            val downReport = ByteArray(8).apply {
                this[0] = modifiers
                this[2] = keyCode
            }
            val upReport = ByteArray(8).apply {
                this[0] = modifiers
            }
            writeReportInternal(downReport)
            delay(30)
            writeReportInternal(upReport)
        }
    }

    private fun closeWritersInternal() {
        try {
            directOut?.close()
        } catch (_: Exception) {
        } finally {
            directOut = null
        }

        try {
            rootWriterStdin?.write("exec 3>&-\nexit\n")
            rootWriterStdin?.flush()
            rootWriterStdin?.close()
        } catch (_: Exception) {
        } finally {
            rootWriterStdin = null
        }

        try {
            rootWriterProcess?.destroy()
        } catch (_: Exception) {
        } finally {
            rootWriterProcess = null
        }
    }

    override fun release() {
        usbExecutor.execute {
            if (isInitialized) {
                writeReportInternal(ByteArray(8))
            }
            isInitialized = false
            closeWritersInternal()

            val link = createdSymlinkPath
            val gadget = activeGadgetPath
            val udc = activeUdcName
            createdSymlinkPath = null
            if (!link.isNullOrBlank() && !gadget.isNullOrBlank() && !udc.isNullOrBlank()) {
                val cleanupScript = """
                    echo "" > "$gadget/UDC" 2>/dev/null || true
                    rm -f "$link" 2>/dev/null || true
                    echo "$udc" > "$gadget/UDC" 2>/dev/null || true
                """.trimIndent()
                runSuCommand(cleanupScript, timeoutSec = 4)
                log("Đã gỡ cấu hình USB HID Gadget và khôi phục cổng USB")
            }

            _state.update {
                it.copy(
                    status = ConnectionStatus.DISCONNECTED,
                    statusMessage = "Đã tắt USB HID"
                )
            }
        }
    }
}
