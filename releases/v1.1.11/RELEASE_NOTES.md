# Kboard v1.1.11 Release Notes

## Cai Thien Ket Noi Wi-Fi Hotspot (Phat Mang)

### 1. Co che Quet Direct Unicast cho Android SoftAP
- **Nguyen nhan**: Khi may nhan phat Wi-Fi Hotspot, co che SoftAP mac dinh co the loc cac goi UDP Broadcast (`255.255.255.255` va `192.168.43.255`), khien goi quet tu thiet bi con bi chan.
- **Giai phap trong v1.1.11**:
  - Ap dung **Direct Unicast Probe**: Trong qua trinh quet LAN, may ban phim gui goi tin truc tiep toi `192.168.43.1:8964` (IP SoftAP mac dinh tren Android) va Gateway cua mang hien tai.
  - Goi Unicast giup ket noi truc tiep toi may nhan ma khong bi anh huong boi bo loc broadcast.

### 2. Nhan Dien & Uu Tien IP Hotspot (192.168.43.1)
- **Nguyen nhan nhan dien sai IP**: Khi bat Hotspot va van bat 4G/5G, ham cu co the lay nham card mang di dong (`rmnet_data0`), khien man hinh Receiver hien sai dai IP.
- **Giai phap trong v1.1.11**:
  - Cap nhat `getLocalIpAddress()`: Uu tien dai IP `192.168.43.1` va cac giao dien Wi-Fi/AP (`ap*`, `softap*`, `wlan*`).
  - Bo qua cac card nha mang 4G/5G khi dang co Wi-Fi / Hotspot.
  - Tich hop tien trinh cap nhat IP dinh ky moi 2.5 giay khi trang thai mang thay doi.

### 3. Xu Ly Tien Trinh Lang Nghe Hai Lop (LanServer & KaiusImeService)
- **Sua loi xung dot cong (BindException)**: Thiet lap `reuseAddress = true` tren `LanServer`, giup giao dien Receiver va ban phim he thong `KaiusImeService` chia se cong lang nghe ma khong xung dot.
- **Phan hoi Discovery tu KaiusImeService**: Khi nguoi dung tren may nhan dang mo ung dung khac, ban phim he thong `KaiusImeService` van tiep nhan va phan hoi goi tin quet tim kiem tu may ban phim.

### 4. Phim Tat Ket Noi Hotspot tren Thanh Cong Cu
- Bo sung nut `[Dung Hotspot]` tren thanh `LanConnectionBar`: Ho tro dat nhanh dia chi nhan ve `192.168.43.1:8964`.

## Tep Phat Hanh
- Kboard_v1.1.11.apk (39.8 MB) - File APK gan tag phien ban.
- Kboard.apk (39.8 MB) - File APK tieu chuan.
- Kboard_Receiver.exe (8.28 MB) - Bo nhan Windows dong bo.
