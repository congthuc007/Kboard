# Kboard Release v1.1.9 (Build 11)

## Diem moi trong phien ban v1.1.9:
1. **Don dep tap tin trung gian**:
   - Xoa bo anh logo khong su dung trong app (`app_logo.png`) giup giam dung luong goi APK.
   - Don dep thu muc build trung gian va cache PyInstaller.
2. **Bang Cai dat Ban phim (Modular Keyboard Settings Panel)**:
   - Bo sung nut Cai dat (Settings) tren thanh Header trong che do Bluetooth va Wi-Fi LAN.
   - Giao dien chia 5 danh muc:
     * **Bo go & Ngon ngu (Language & Typing)**: Chon nhanh Tieng Viet (Telex) hoac English, cong tac bat/tat Telex truc tiep tren may gui va dong bo toi may nhan (PC / Android receiver).
     * **Den nen RGB (Backlight Themes)**: 5 chu de mau sac:
       - Dark Industrial (Mac dinh - Toi gian)
       - Cyberpunk Neon (Cyan & Magenta)
       - Matrix Green (Xanh phosphor)
       - RGB Chroma Wave (Chuyen mau cau vong)
       - Retro Amber (Cam hoai niem)
       - Hieu ung Reactive Key Glow: vien phim sang len khi nhan.
     * **Phan hoi Rung & Am thanh (Haptics & Sound)**: Phan hoi rung khi go (3 muc: Nhe 12ms / Vua 22ms / Manh 35ms), tuy chon am thanh click phim.
     * **Toc do lap phim (Hardware Key Repeat)**: Tuy chinh thoi gian tre bat dau (250ms / 380ms / 500ms) va toc do lap ky tu (25ms / 45ms / 70ms).
     * **Mang LAN & IP**: Cau hinh IP/Port may nhan, goi y nhanh IP Hotspot, nut gui goi Ping kiem tra mang.

## File phat hanh kem theo:
* `Kboard_v1.1.9.apk`: Ban cai dat Android (API 28+).
* `Kboard.apk`: Ban cai dat Android tieu chuan.
* `Kboard_Receiver.exe`: Bo nhan doc lap cho Windows (x86_64).
