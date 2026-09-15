# Kboard Release v1.1.6 (versionCode 8)

## Điểm mới trong phiên bản v1.1.6:
1. **Cân chỉnh kích thước logo (Icon Padding & Safe Zone):**
   - Biểu tượng bàn phím trắng được tinh chỉnh kích thước về tỷ lệ **49%** (thay vì chiếm gần 70% như trước).
   - Thiết lập khoảng đệm an toàn (~25% bốn phía) nằm bên trong vòng tròn an toàn 66dp theo tiêu chuẩn Android Adaptive Icon.
   - Khi áp dụng trên MIUI, HyperOS, OneUI hay Pixel Launcher, biểu tượng bàn phím nằm cân đối trong vùng hiển thị an toàn, hạn chế tình trạng lẹm góc hoặc chạm viền bo cong.
2. **Đồng bộ các định dạng Icon:**
   - `ic_launcher.png`: Chuẩn squircle viền liền mạch.
   - `ic_launcher_round.png`: Chuẩn tròn căn tâm.
   - `ic_launcher_background.png` & `ic_launcher_foreground.png`: Chuẩn Adaptive Icons tách lớp.
   - `receiver_icon.ico`: Đồng bộ định dạng cho ứng dụng nhận phím trên Windows.

## Tệp đính kèm trong thư mục:
* `Kboard_v1.1.6.apk`: Bản cài đặt Android (API 28+).
* `Kboard.apk`: File cài đặt chuẩn.
* `Kboard_Receiver.exe`: Bộ nhận phím độc lập cho máy tính Windows.
