# Kboard Release v1.1.8 (Build 10)

## Diem moi trong phien ban v1.1.8:
1. **Tu dong xoa cac may nhan offline (Eviction TTL 6s)**:
   - Co che do tim UDP bo sung co che don rac dinh ky (chay moi 2 giay).
   - Neu thiet bi nhan ngat mang hoac tat ung dung, sau 6 giay khong co phan hoi probe, the thiet bi se tu dong duoc go khoi thanh ket noi.
2. **Lam moi danh sach khi bam [Quet LAN]**:
   - Khi bam nut `[Quet LAN]`, danh sach thiet bi duoc lam moi (`emptyList()`).
   - Phat broadcast probe den cac giao dien mang va dia chi `255.255.255.255`.
   - Chi nhung thiet bi dang online va phan hoi moi hien thi tren man hinh.
3. **Chi bao truc quan khi dang quet**:
   - Hieu ung xoay bieu tuong Refresh va dong trang thai "Dang tim may nhan trong mang LAN..." trong luc do tim mang.
4. **Toi uu bo nho Gradle JVM**:
   - Dieu chinh `-Xmx2048m` de giu tien trinh build on dinh.

## File phat hanh kem theo:
* `Kboard_v1.1.8.apk`: Ban cai dat Android (API 28+).
* `Kboard.apk`: Ban cai dat Android tieu chuan.
* `Kboard_Receiver.exe`: Bo nhan doc lap cho Windows (x86_64).
