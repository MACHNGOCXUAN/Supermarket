// Thêm khách hàng
//@Column(name="ma_khach_hang")
//private String maKhachHang;
//@Column(name = "ten_khach_hang")
//private String tenKhachHang;
//@Column(name = "so_dien_thoai", columnDefinition = "varchar(11)", unique = true, nullable = false)
//private String soDienThoai;
//@Column(name="gioi_tinh")
//@Enumerated(EnumType.STRING)
//private GioiTinh gioiTinh;
//@Column(name="diem_tich_luy")
//private int diemTichLuy;
CREATE (kh: KhachHang)
SET kh.maKhachHang = "KH00001",
kh.tenKhachHang = "Test",
kh.soDienThoai = "0899132213",
kh.gioiTinh = "Nam",
kh.diemTichLuy = 2000


//Tỉm khách hàng theo id

MATCH(kh:KhachHang{maKhachHang:"KH00004"})
RETURN kh;


// Update
MATCH (kh: KhachHang{maKhachHang: "KH00004"})
SET kh.tenKhachHang = "Hã Mãnh Cưỡng",
kh.soDienThoai = "099999999",
kh.gioiTinh = "Nam",
kh.diemTichLuy = 2000

// Them hoa don

CREATE (hd:HoaDon)
SET hd.maHoaDon = "HD0001",
hd.ngayLapHoaDon = date("2025-11-11"),
hd.diemTichLuySuDung = 10,
hd.tongTien = 10000,
hd.ghiChu = "Khong co"


SELECT n FROM NhanVien n WHERE n.taiKhoan IS NOT NULL