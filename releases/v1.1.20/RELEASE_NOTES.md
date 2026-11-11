# Kboard v1.1.20 Release Notes

## Cai tien va Sua loi

### 1. Cong tac Bat/Tat Ban Phim Ao 75% & Luu Trang Thai
- Thay the nut nguon don gian bang **Cong tac gat Bat/Tat (`HeaderKeyboardSwitch`)** hien thi ro trang thai `BAT` / `TAT` ngay tren thanh tieu de.
- Khi mo app o trang thai **TAT**, man hinh hien thi bang **Trang thai Ban phim (`KeyboardStandbyScreen`)** kem cong tac lon, trang thai ket noi Bluetooth/Wi-Fi LAN va cac thao tac nhanh (Cau hinh, Cai dat, Doi ban phim, Thu gon) thay vi tu dong bung ban phim 75% ngay khi mo app.
- **Luu trang thai qua SharedPreferences (`is_keyboard_active`)**: Mac dinh khi cai moi la **TAT (`false`)**; khi nguoi dung gat cong tac BAT hoac TAT, trang thai duoc luu lai cho cac lan mo app tiep theo.
- Khi gat **TAT**: Tu dong nha cac phim bo tro dang giu (Ctrl/Shift/Alt/Win), tat giu sang man hinh (`FLAG_KEEP_SCREEN_ON`) va dung quet LAN Discovery de tiet kiem pin.

## Danh muc tep phat hanh
- `Kboard_v1.1.20.apk`: Ban cai dat APK Android v1.1.20 (`versionCode 22`).
- `Kboard.apk`: Ban APK chuan tai thu muc goc va thu muc release.
- `Kboard_Receiver.exe`: Bo nhan Windows dong bo.

