# Kboard v1.1.10 Release Notes

## Diem Moi & Nang Cap Chinh

### 1. Cau hinh bo go Tieng Viet Telex tap trung tren May Ban Phim
- Bo go Tieng Viet Telex duoc bat/tat va dieu chinh truc tiep tren thiet bi dong vai tro Ban Phim (Kboard Sender).
- May nhan (Receiver / KaiusImeService) hien thi huy hieu trang thai [VI] / [EN] dong bo theo goi lenh tu ban phim.
- Tren ban phim: Nguoi dung co the chuyen nhanh che do VI / EN ngay tren QuickMacroBar hoac trong Keyboard Settings Dialog.

### 2. Che do nghi tiet kiem pin (Standby / Sleep Mode)
- Nut nguon tich hop truc tiep tren thanh tieu de Landscape Unified Header:
  - Trang thai Xanh luc: Ban phim dang hoat dong (Active).
  - Trang thai Vang ho phach: Ban phim dang o che do nghi (Standby).
- Khi chuyen sang che do Standby:
  - Tu dong ha co `FLAG_KEEP_SCREEN_ON` de man hinh dien thoai co the tu dong tat theo thoi gian cho cua he thong.
  - Tam dung phat broadcast LAN Discovery de tiet kiem pin va bang thong Wi-Fi.
  - Lop phu StandbyOverlay ho tro ngan cham nham phim khi chua dung den.
  - Cung cap 3 thao tac nhanh ngay tren overlay:
    + [ BAT LAI BAN PHIM ]: Kich hoat lai ban phim.
    + [ Doi Ban Phim Thuong (Gboard) ]: Mo trinh chon phuong thuc nhap he thong de chuyen ve ban phim thuong.
    + [ Ve Man Hinh Chinh ]: Chuyen ung dung xuong chay nen.

### 3. Don dep ma nguon & Toi uu tai nguyen
- Loai bo cac tep hinh anh du thua, giam kich thuoc goi cai dat.
- Toi uu hoa dong bo StateFlow giua ViewModel va Jetpack Compose UI.

## Tep Phat Hanh
- Kboard_v1.1.10.apk (39.8 MB) - File APK gan tag phien ban.
- Kboard.apk (39.8 MB) - File APK tieu chuan.
- Kboard_Receiver.exe (8.28 MB) - Bo nhan Windows dong bo.
