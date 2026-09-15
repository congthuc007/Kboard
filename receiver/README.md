# Kboard Wi-Fi LAN Receiver

Bộ nhận phím qua Wi-Fi nội bộ / Hotspot cho máy tính (Windows / Linux / Mac).

## Chạy trên Windows:
Không cần cài thêm thư viện phụ thuộc (sử dụng trực tiếp Windows API `SendInput` thông qua `ctypes`).

```powershell
python pc_receiver.py
```

Khi chạy, script sẽ in ra địa chỉ IP của máy tính (ví dụ: `192.168.1.15:8964`).
Kết nối qua app Kboard (tự động phát hiện qua nút [Quét LAN] hoặc nhập IP thủ công), các thao tác gõ phím trên điện thoại sẽ được truyền tới máy tính.
