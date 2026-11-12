# Kboard v1.1.21 Release Notes

## Cai tien va Tinh nang moi

### 1. Tich hop che do USB HID Gadget (Ho tro may da Root)
- Bo sung `TransportMode.USB_HID` va `UsbHidTransport` trien khai chuan `InputTransport`.
- Tren thiet bi da Root (`su`) va kernel ho tro USB HID Gadget (`f_hid` / `/dev/hidg0`):
  - Tu dong cau hinh ConfigFS (`/config/usb_gadget`) voi USB Boot Keyboard HID Descriptor 63-byte (protocol=1, subclass=1, report_length=8).
  - Truyen goi HID 8-byte truc tiep qua `/dev/hidg0` (ho tro Direct I/O va kenh Root Shell du phong khi SELinux chan).
  - Cam cap USB vao Laptop/PC se nhan truc tiep nhu ban phim USB phan cung (Plug & Play, khong can cai `Kboard_Receiver.exe`), hoat dong ca o BIOS/UEFI va man hinh khoa.
  - Tu dong go symlink ConfigFS va khoi phuc UDC khi chuyen che do hoac thoat ung dung.
- Tren thiet bi chua Root:
  - Khong anh huong toi 2 che do mac dinh (`Bluetooth` va `Wi-Fi LAN`).
  - Neu nguoi dung chon tab `USB HID`, giao dien bao trang thai ro rang (`Can Root (su) de dung USB HID`) va khong gay loi ung dung.

## Danh muc tep phat hanh
- `Kboard_v1.1.21.apk`: Ban cai dat APK Android v1.1.21 (`versionCode 23`).
- `Kboard.apk`: Ban APK chuan tai thu muc goc va thu muc release.
- `Kboard_Receiver.exe`: Bo nhan Windows dong bo.

