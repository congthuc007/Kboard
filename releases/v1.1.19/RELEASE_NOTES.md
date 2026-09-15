# Kboard v1.1.19 Release Notes

## Cai tien va Sua loi

### 1. Khoi phuc kien truc ban 14 cho KaiusImeService
- File `KaiusImeService.kt` duoc khoi phuc tu commit ban 14 (`6b51eb3`).
- Giu co che injectKeyEvent ket hop performContextMenuAction khi go tu ban phim.
- Khong can thiep vao ma KEYCODE_HOME, tranh loi thoat ung dung clone khi nhan phim.

### 2. Dieu chinh co che gui su kien cho cum nut Macro Ctrl (Ctrl+A, Ctrl+C, Ctrl+V, Ctrl+Z, Alt+Tab)
- Nguyen nhan: Khi nhan nut Macro o phien ban truoc, he thong goi ma phim ma chua truyen su kien nhan giu Ctrl xuong Android, khien he thong nhan dien la phim go thuong ('a', 'c', 'v').
- Khac phuc: Tinh chinh `sendMacro` trong `InputEngine.kt` theo trinh tu:
  1. Gui su kien giu phim bo tro (Ctrl/Alt).
  2. Gui tiep ma phim can thuc hien (A, C, V, Z, Tab) trong khi phim bo tro dang duoc giu.
  3. Nha phim ky tu, sau do nha phim bo tro va xoa report.
- Nho do, cum nut Macro tren dau truyen dung to hop phim toi ung dung dang hoat dong.

### 3. Giam thoi gian cho xu ly nut Win
- Giam thoi gian cho trong `sendMacro` tu 40ms xuong 15ms, giup thao tac nhan nut Win phan hoi nhanh hon.

## Danh muc tep phat hanh
- Kboard_v1.1.19.apk: Ban cai dat APK Android v1.1.19 (versionCode 21, cai de truc tiep).
- Kboard.apk: Ban APK chuan tai thu muc goc va thu muc release.
- Kboard_Receiver.exe: Bo nhan Windows dong bo.
