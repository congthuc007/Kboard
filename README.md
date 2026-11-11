# Kboard - Bàn phím cơ ảo & Bộ nhận tín hiệu PC

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20Windows-00E5FF?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Bluetooth](https://img.shields.io/badge/Bluetooth-HID%20Profile-0075FF?style=for-the-badge&logo=bluetooth&logoColor=white)
![Network](https://img.shields.io/badge/LAN-UDP%20Auto--Discovery-00C853?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-v1.1.20-FF6D00?style=for-the-badge)

**Ứng dụng biến điện thoại thông minh thành bàn phím cơ ảo cho Máy tính, Laptop và Thiết bị Android khác qua kết nối Wi-Fi LAN hoặc Bluetooth.**

<br/>

[![Download Android APK](https://img.shields.io/badge/Tải_ngay-Kboard.apk_(Android)-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/congthuc007/Kboard/releases/download/v1.1.20/Kboard.apk)
[![Download Windows Receiver](https://img.shields.io/badge/Tải_ngay-Kboard_Receiver.exe_(Windows)-0078D6?style=for-the-badge&logo=windows&logoColor=white)](https://github.com/congthuc007/Kboard/releases/download/v1.1.20/Kboard_Receiver.exe)
[![View All Releases](https://img.shields.io/badge/Tất_cả_phiên_bản-GitHub_Releases-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/congthuc007/Kboard/releases)

<br/>

**Tác giả:** [Nguyễn Công Thức (Kaius)](https://github.com/congthuc007) • **Phiên bản mới nhất:** [v1.1.20 Release](https://github.com/congthuc007/Kboard/releases/tag/v1.1.20)

</div>

---

## 1. Giới thiệu tổng quan

**Kboard** hỗ trợ nhập liệu với kiến trúc **Dual-Role (Bàn phím / Máy nhận)** và **Dual-Transport (Bluetooth HID + Wi-Fi LAN)**:
- Biến điện thoại thành **bàn phím rời layout 75%** với đầy đủ các phím điều hướng, hàng phím chức năng F1-F12, phím số, phím bổ trợ Ctrl/Alt/Win/Shift và thanh phím tắt macro.
- Hỗ trợ gửi phím trực tiếp tới **Máy tính/Laptop Windows** (thông qua bộ nhận độc lập không cần cài driver) hoặc gửi phím sang **Điện thoại khác** để điều khiển, chơi game hoặc nhập liệu vào các **ứng dụng clone (App Clone / Dual Apps)**.

---

## 2. Tính năng công nghệ nổi bật

### Layout 75% PC Keyboard
* **Tối ưu hóa xoay ngang (sensorLandscape)**: Giao diện tràn viền (Edge-to-Edge) bố trí cho thao tác 2 ngón cái, thiết kế cơ học Dark Mode rõ ràng.
* **Đầy đủ 6 hàng phím PC**: Đầy đủ `Esc`, hàng `F1` - `F12`, phím `Del`, dãy số & ký tự đặc biệt, phím điều hướng `▲ ▼ ◄ ►`, `Tab`, `Caps Lock`, `Enter`, `Backspace`, `Ctrl`, `Alt`, `Win`, `Shift`.
* **Hardware Key Repeat (Đè phím lặp ký tự)**: Tích hợp engine lặp phím — nhấn giữ phím quá 380ms sẽ kích hoạt chu kỳ lặp phím 45ms (~22 ký tự/giây) tương tự phím vật lý.
* **Thanh Macro 1 chạm**: Tích hợp sẵn các tổ hợp phím phổ biến: `Ctrl+C`, `Ctrl+V`, `Ctrl+A`, `Ctrl+Z`, `Alt+Tab`, `Win`.

### Cơ chế truyền tải (Dual-Transport)
* **Bluetooth HID Device Profile**:
  * Đóng vai trò là thiết bị ngoại vi USB HID Keyboard.
  * Nếu thiết bị gửi hỗ trợ Bluetooth HID Device, máy nhận (PC, Laptop, Smartphone) có thể nhận diện trực tiếp như bàn phím Bluetooth mà **không cần cài thêm phần mềm trên máy nhận**.
* **Wi-Fi LAN UDP Bridge**:
  * Hoạt động thông qua mạng Wi-Fi nội bộ bằng giao thức UDP trên cổng 8964.
  * **Tự động dò tìm thiết bị (Auto Discovery)**: Thiết bị gửi hỗ trợ quét và phát hiện các máy nhận đang trực tuyến trong cùng mạng LAN để kết nối nhanh.

### Bộ gõ Tiếng Việt Telex & Bàn phím ảo hệ thống (Android IME)
* **Tích hợp sẵn Kboard IME (InputMethodService)**:
  * Hoạt động như một bộ gõ hệ thống trên điện thoại nhận.
  * Cho phép gõ phím trực tiếp vào **App Clone, ứng dụng chat, game giả lập** mà không bị giới hạn bởi sandbox bảo mật của Android.
* **Bộ gõ Tiếng Việt Telex tích hợp**:
  * Tự động nhận diện và xử lý dấu tiếng Việt theo chuẩn Telex (`aa` -> `â`, `aw` -> `ă`, `dd` -> `đ`, `s/f/r/x/j` bỏ dấu tự nhiên).
  * Nút chuyển đổi nhanh `[ VI ]` / `[ EN ]` trực tiếp trên thanh Macro và trong hộp thoại Cài đặt, tự động đồng bộ tới máy nhận theo thời gian thực.

### Windows Companion Receiver (Kboard_Receiver.exe)
* Bộ nhận độc lập (Portable) cho Windows với giao diện **Desktop GUI Dark Mode**, không cần cài đặt môi trường Python hay driver bổ sung.
* Sử dụng Windows API `SendInput` kết hợp scancode `user32.MapVirtualKeyW`, chuyển tiếp tín hiệu phím tới các ứng dụng và game trên Windows.
* Tích hợp Single-Instance Mutex ngăn mở đè tiến trình, màn hình giám sát phím thời gian thực và nút sao chép IP nhanh.

---

## 3. Sơ đồ kiến trúc hệ thống

```text
                           ┌───────────────────────────────┐
                           │          KBOARD APP           │
                           │   (Giao diện 75% PC Layout)   │
                           └───────────────┬───────────────┘
                                           │
                    ┌──────────────────────┴──────────────────────┐
                    │                                             │
         [ Bluetooth HID Mode ]                         [ Wi-Fi LAN UDP Mode ]
         Profile HID Device (SDP)                       Cổng UDP 8964 + Auto Discovery
                    │                                             │
        ┌───────────┴───────────┐                         ┌───────┴───────┐
        ▼                       ▼                         ▼               ▼
┌──────────────┐        ┌──────────────┐           ┌──────────────┐ ┌──────────────┐
│   LAPTOP /   │        │  ĐIỆN THOẠI  │           │  ĐIỆN THOẠI  │ │   KBOARD     │
│   MÁY TÍNH   │        │     NHẬN     │           │ (KBOARD IME) │ │  RECEIVER    │
│(Nhận như kbd │        │(Pair trực    │           │(Gõ App Clone,│ │ (SendInput   │
│  Bluetooth)  │        │ tiếp qua BT) │           │ game, Telex) │ │  Scancodes)  │
└──────────────┘        └──────────────┘           └──────────────┘ └──────────────┘
```

---

## 4. Tải về & Hướng dẫn sử dụng

### Bảng tải về trực tiếp (Downloads)

| Nền tảng | Tệp cài đặt | Nút tải trực tiếp (1-Click) | Ghi chú |
| :--- | :--- | :---: | :--- |
| **Android** | `Kboard.apk` | [**Tải Kboard.apk**](https://github.com/congthuc007/Kboard/releases/download/v1.1.20/Kboard.apk) | Bản cài đặt cho điện thoại làm bàn phím hoặc điện thoại nhận |
| **Windows** | `Kboard_Receiver.exe` | [**Tải Windows Receiver**](https://github.com/congthuc007/Kboard/releases/download/v1.1.20/Kboard_Receiver.exe) | Giao diện Desktop GUI chạy ngay trên Laptop/PC Windows (không cần cài driver) |
| **Lịch sử** | Bản ổn định | [**Xem GitHub Releases**](https://github.com/congthuc007/Kboard/releases) | Kho lưu trữ các phiên bản phát hành chính thức |

---

### Kịch bản 1: Kết nối Wi-Fi LAN sang Điện thoại nhận (Hỗ trợ App Clone)
1. **Trên điện thoại nhận**:
   * Vào **Cài đặt** -> **Hệ thống & Bàn phím** -> Bật kích hoạt **Bàn phím Kboard LAN**.
   * Mở app Kboard -> Chọn vai trò `[ Nhận phím ]`.
   * Mở ứng dụng cần gõ (App clone, game, trình duyệt...) và chọn bàn phím ảo là **Kboard LAN**.
2. **Trên điện thoại làm bàn phím**:
   * Mở app Kboard -> Chọn `[ Bàn phím ]` -> Chọn tab `[ Wi-Fi LAN ]`.
   * App sẽ tự động quét và hiện tên máy nhận trên thanh kết nối. Bấm vào tên máy để kết nối ngay.
   * Gõ chữ, phím tắt `Ctrl+C`, `Ctrl+V`, `Ctrl+A` sẽ được truyền trực tiếp vào app clone.

---

### Kịch bản 2: Kết nối Wi-Fi LAN sang Máy tính / Laptop Windows
1. Trên máy tính Windows: Khởi chạy file `Kboard_Receiver.exe` (giao diện đồ họa sẽ hiển thị địa chỉ IP của máy tính).
2. Trên điện thoại: Chọn chế độ `[ Wi-Fi LAN ]` -> Bấm nút **[ Quét LAN ]** -> Chọn thiết bị PC vừa tìm thấy (hoặc nhập trực tiếp IP hiển thị trên Kboard Receiver).
3. Bắt đầu gõ phím, điều khiển trình chiếu, chơi game hoặc gõ văn bản từ xa.

---

### Kịch bản 3: Kết nối Bluetooth HID trực tiếp
*(Áp dụng cho các dòng điện thoại Android có hỗ trợ Bluetooth HID Device Profile ở cấp phần cứng)*
1. Trên điện thoại làm bàn phím: Chọn tab `[ Bluetooth HID ]` -> Bấm nút phát hiện (`Discoverable`).
2. Trên thiết bị nhận (Laptop hoặc điện thoại khác): Mở Bluetooth máy -> Dò tìm thiết bị tên **Kboard** hoặc tên điện thoại của bạn -> Bấm **Ghép đôi (Pair)**.
3. Sau khi kết nối thành công, điện thoại sẽ trở thành bàn phím không dây độc lập.

---

## 5. Công nghệ & Ngôn ngữ sử dụng

* **Android:** Kotlin 1.9+, Jetpack Compose, Coroutines Flow, AndroidX Lifecycle, Android IME (`InputMethodService`).
* **Networking:** Java NIO DatagramChannel & DatagramSocket UDP, Broadcast Discovery protocol.
* **Bluetooth:** Android Bluetooth HID Device API (`BluetoothHidDevice`), SDP Keyboard Descriptor.
* **Windows Host:** Python 3 / PyInstaller standalone exe, Windows Win32 API (`SendInput`, `MapVirtualKeyW`).

---

## 6. Lịch sử Phiên bản & Độ ổn định

| Phiên bản | Trạng thái | Chi tiết |
| :--- | :--- | :--- |
| **v1.1.20** | **Ổn định** | Bổ sung màn hình trạng thái & công tắc gạt Bật/Tắt bàn phím 75% (mặc định Tắt khi cài mới, lưu trạng thái qua các lần mở app). |
| **v1.1.19** | **Ổn định (Cũ)** | Khôi phục kiến trúc IME v14, xử lý sự kiện phím bổ trợ cho tổ hợp Macro Ctrl+A/C/V/Z, giảm thời gian trễ nút Win xuống 15ms, cập nhật Receiver GUI. |
| v1.1.15 - v1.1.18 | **Đã thu hồi (Không sử dụng)** | Các bản thử nghiệm có lỗi thoát app clone khi bấm Win hoặc bị chặn sự kiện bàn phím. Đã gỡ khỏi GitHub Releases. |
| v1.1.14 | **Ổn định (Cũ)** | Bản ổn định cột mốc hỗ trợ IME phần cứng. |
| v1.1.4 - v1.1.13 | **Lưu trữ** | Các phiên bản phát triển tính năng ban đầu. |

---

## 7. Tác giả
 
* **Kỹ sư phát triển:** **Nguyễn Công Thức (Kaius)**
* **GitHub:** [https://github.com/congthuc007](https://github.com/congthuc007)
* **Dự án:** *Kboard Ecosystem*

---

<div align="center">
  <i>Kboard - Emulated Input System for Android & Windows</i>
</div>
