# Kboard v1.1.14 Release Notes

## Cai Thien Phim Bo Tro (Ctrl, Win, Alt, Shift) & Phim Tat Cho App Clone

### 1. Truyen Ma Su Kien Phim He Thong Android (Key Injection)
- **Truyen ma phim he thong (KEYCODE_CTRL_LEFT, KEYCODE_META_LEFT, KEYCODE_ALT_LEFT, KEYCODE_SHIFT_LEFT)**:
  - Cac ban truoc day khi nhan ma phim bo tro chi cap nhat chuoi ky tu ma chua truyen ma phim xuong Android, khien cac ung dung clone, gia lap hoac trinh duyet khong nhan duoc trang thai modifier.
  - Ban v1.1.14 truyen truc tiep ma phim he thong vao Android:
    - Bam `Ctrl`: truyen `KEYCODE_CTRL_LEFT` (ACTION_DOWN khi bat, ACTION_UP khi tat).
    - Bam `Win`: truyen `KEYCODE_META_LEFT` (Windows / Meta key tren Android).
    - Bam `Alt`: truyen `KEYCODE_ALT_LEFT`.
    - Bam `Shift`: truyen `KEYCODE_SHIFT_LEFT`.

### 2. Dong Bo Ca 3 Dang Hanh Dong: DOWN, UP, TAP
- **Mo rong cau noi noi bo (LanBridge)**:
  - Cau noi `LanBridge` truyen day du tham so hanh dong (`action`: DOWN, UP, TAP) giua bo thu mang va ban phim ao he thong.
  - Bo thu doc lap (`startStandaloneListener`) tiep nhan va phan phoi ca 3 loai hanh dong thay vi chi loc rieng `ACTION_KEY_DOWN` nhu truoc day.

### 3. Xu Ly Cho App Clone, Game & Gia Lap (No Focused Text Field)
- **Dieu phoi su kien phim khi khong co focus nhap lieu**:
  - Khi ung dung clone, gia lap (Winlator, Termux, Game, Remote Desktop) khong co o nhap van ban (cursor focus la null), he thong su dung co che `sendDownUpKeyEvents` de truyen ma phim xuong ung dung dang hien hanh.
- **Phim tat Ctrl+C, Ctrl+V, Ctrl+A, Ctrl+X, Ctrl+Z**:
  - Tich hop ca trinh dieu khien context menu va ma phim `KeyEvent` co `META_CTRL_ON`, giup ung dung clone va trinh duyet tiep nhan lenh copy/paste/select all.

### 4. Tep Phat Hanh
- Kboard_v1.1.14.apk (39.8 MB) - File APK gan tag phien ban.
- Kboard.apk (39.8 MB) - File APK tieu chuan.
- Kboard_Receiver.exe (8.28 MB) - Bo nhan Windows dong bo.
