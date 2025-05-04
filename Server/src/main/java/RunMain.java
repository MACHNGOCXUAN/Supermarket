import dao.impl.*;
import model.*;
import net.datafaker.Faker;
import service.impl.TaiKhoanServiceImpl;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RunMain {
    private static final Scanner sc = new Scanner(System.in);

    private static final Faker faker = new Faker();

    public static void main(String[] args) throws RemoteException {
        int lc;
        do{
            System.out.println("-------------------------Menu test--------------------------");
            System.out.println("1. Sản phẩm");
            System.out.println("2. Tài khoản");
            System.out.println("3. Khách hàng");
            System.out.println("4. Nhân viên");
            System.out.println("5. Khuyến Mãi");
            System.out.println("6. Hóa đơn");
            System.out.println("7. Danh mục sản phẩm");
            System.out.println("0. Thoát");
            System.out.println("Mời bạn nhập lựa chọn: ");
            lc = sc.nextInt();

            switch (lc){
                case 1:
                    SanPham_Menu();
                    break;
                case 2:
                    TaiKhoan_Menu();
                    break;
                case 3:
                    KhachHang_Menu();
                    break;
                case 4:
                    NhanVien_Menu();
                    break;
                case 5:
                    KhuyenMai_Menu();
                    break;
                case 6:
                    HoaDon_Menu();
                    break;
                case 7:
                    DanhMucSanPham_Menu();
                    break;
            }


        }while (lc != 0);

        System.out.println("Bạn đã thoát chương trình");



    }

    public static void SanPham_Menu() throws RemoteException {
        int lc;
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xóa");
            System.out.println("4. Xem full danh sách");
            System.out.println("5. Tìm theo mã");
            System.out.println("0. Thoát");

            SanPhamDAOImpl sanPhamDAOImpl = new SanPhamDAOImpl();
            lc = sc.nextInt();

            switch (lc){
                case 1:{
                    sc.nextLine();
                    System.out.println("Nhập tên sản phẩm: ");
                    String tenSanPham = sc.nextLine();
                    String maSanPham = "SP" + faker.number().digits(5);
                    System.out.println("Nhập hạn sử dụng sản phẩm: ");
                    String hanSD = sc.nextLine();
                    LocalDate hanSuDung = LocalDate.parse(hanSD, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.println("Nhập giá bán: ");
                    Double giaBan = sc.nextDouble();
                    System.out.println("Nhập thuế VAT: ");
                    Double thueVAT = sc.nextDouble();
                    String trangThai = "Còn bán";
                    System.out.println("Nhập số lượng tồn: ");
                    int soLuong = sc.nextInt();

                    LocalDate ngayNhap = LocalDate.now();

                    // Tạo mô tả
                    String moTa = "Sản phẩm: " + tenSanPham +
                            ". Được sản xuất tại " + faker.address().country() +
                            " với tiêu chuẩn " + faker.company().name() + ".";

                    DanhMucSanPhamDAOImpl danhMucSanPhamDAOImpl = new DanhMucSanPhamDAOImpl();

                    int count = 0;
                    for (DanhMucSanPham danhMucSanPham : danhMucSanPhamDAOImpl.getList()){
                        System.out.println(count + ". " + danhMucSanPham);
                        count += 1;
                    }

                    System.out.println("Chọn danh mục sản phẩm ứng với các số");
                    int chon = sc.nextInt();
                    DanhMucSanPham danhMucSanPham = danhMucSanPhamDAOImpl.getList().get(chon);

                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPham(maSanPham);
                    sanPham.setTenSanPham(tenSanPham);
                    sanPham.setDanhMucSanPham(danhMucSanPham);
                    sanPham.setNgayNhap(ngayNhap);
                    sanPham.setMoTa(moTa);
                    sanPham.setGiaBan(giaBan);
                    sanPham.setThueVAT(thueVAT);
                    sanPham.setHanSuDung(hanSuDung);
                    sanPham.setTrangThai(trangThai);
                    sanPham.setSoLuongTon(soLuong);

                    sc.nextLine();
                    System.out.println("Nhập các thuộc tính khác (nếu có)");
                    String thuocTinh = sc.nextLine();

                    ThuocTinhSanPhamDAOImpl thuocTinhSanPhamDAOImpl = new ThuocTinhSanPhamDAOImpl();
                    List<ThuocTinhSanPham> thuocTinhSanPhams =
                            Arrays.stream(thuocTinh.split(" "))
                            .map(tt ->
                                   new ThuocTinhSanPham(
                                           "TT" + faker.number().digits(5),
                                           tt.split("_")[0],
                                           tt.split("_")[1], sanPham)).toList();

//                    sanPham.setThuocTinhSanPhams(thuocTinhSanPhams);
                    boolean isThem = sanPhamDAOImpl.save(sanPham);
                    if(isThem){
                        System.out.println("Thêm sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi thêm !!!!");
                    for (ThuocTinhSanPham tt : thuocTinhSanPhams)
                        thuocTinhSanPhamDAOImpl.save(tt);
                    break;

                }
                case 2:{
                    sc.nextLine();
                    System.out.println("Nhập mã sản phẩm cần sửa: ");
                    String maSanPham = sc.nextLine();
                    System.out.println("Nhập tên sản phẩm: ");
                    String tenSanPham = sc.nextLine();
                    System.out.println("Nhập hạn sử dụng sản phẩm: ");
                    String hanSD = sc.nextLine();
                    LocalDate hanSuDung = LocalDate.parse(hanSD, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.println("Nhập giá bán: ");
                    Double giaBan = sc.nextDouble();
                    System.out.println("Nhập thuế VAT: ");
                    Double thueVAT = sc.nextDouble();
                    String trangThai = "Còn bán";
                    System.out.println("Nhập số lượng tồn: ");
                    int soLuong = sc.nextInt();

                    LocalDate ngayNhap = LocalDate.now();

                    // Tạo mô tả
                    String moTa = "Sản phẩm: " + tenSanPham +
                            ". Được sản xuất tại " + faker.address().country() +
                            " với tiêu chuẩn " + faker.company().name() + ".";

                    DanhMucSanPhamDAOImpl danhMucSanPhamDAOImpl = new DanhMucSanPhamDAOImpl();

                    int count = 0;
                    for (DanhMucSanPham danhMucSanPham : danhMucSanPhamDAOImpl.getList()){
                        System.out.println(count + ". " + danhMucSanPham);
                        count += 1;
                    }

                    System.out.println("Chọn danh mục sản phẩm ứng với các số");
                    int chon = sc.nextInt();
                    DanhMucSanPham danhMucSanPham = danhMucSanPhamDAOImpl.getList().get(chon);

                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPham(maSanPham);
                    sanPham.setTenSanPham(tenSanPham);
                    sanPham.setDanhMucSanPham(danhMucSanPham);
                    sanPham.setNgayNhap(ngayNhap);
                    sanPham.setMoTa(moTa);
                    sanPham.setGiaBan(giaBan);
                    sanPham.setThueVAT(thueVAT);
                    sanPham.setHanSuDung(hanSuDung);
                    sanPham.setTrangThai(trangThai);
                    sanPham.setSoLuongTon(soLuong);

                    sc.nextLine();
                    System.out.println("Nhập các thuộc tính khác (nếu có)");
                    String thuocTinh = sc.nextLine();

                    boolean isSua;
                    ThuocTinhSanPhamDAOImpl thuocTinhSanPhamDAOImpl = new ThuocTinhSanPhamDAOImpl();
                    List<ThuocTinhSanPham> thuocTinhSanPhams = new ArrayList<>();

                    if(!thuocTinh.trim().equalsIgnoreCase("")){
                        for(ThuocTinhSanPham thuocTinhSanPham : thuocTinhSanPhamDAOImpl.getListByProductId(maSanPham)){
                            thuocTinhSanPhamDAOImpl.delete(thuocTinhSanPham.getMaThuocTinhSanPham());
                        }

                        thuocTinhSanPhams =
                                Arrays.stream(thuocTinh.split(" "))
                                        .map(tt ->
                                                new ThuocTinhSanPham("TT" + faker.number().digits(5),
                                                        tt.split("_")[0],
                                                        tt.split("_")[1],
                                                        sanPham)).toList();

                        isSua = sanPhamDAOImpl.update(sanPham);
                        thuocTinhSanPhams.forEach(tt -> {

                            thuocTinhSanPhamDAOImpl.save(tt);

                        });
                    } else{
                        isSua = sanPhamDAOImpl.update(sanPham);
                    }


                    if(isSua){
                        System.out.println("Sửa sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi sửa !!!!");
                    break;
                }
                case 3:{
                    sc.nextLine();
                    System.out.println("Nhập mã sản phẩm cần xóa: ");
                    String maSP = sc.nextLine();
                    boolean isXoa = sanPhamDAOImpl.delete(maSP);
                    if(isXoa){
                        System.out.println("Xóa sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi xóa !!!!");
                    break;
                }
                case 4: {
                    sanPhamDAOImpl.getList().forEach(x -> System.out.println(x));
                    break;
                }
                case 5:{
                    sc.nextLine();
                    System.out.println("Nhập mã sản phẩm cần tìm: ");
                    String maSP = sc.nextLine();
                    SanPham sanPham = sanPhamDAOImpl.findOne(maSP);
                    if(sanPham != null){
                        System.out.println(sanPham);
                    } else System.out.println("Không tìm thấy sản phẩm này !!!!");
                    break;
                }

            }

        }while (lc != 0);
    }

    public static void TaiKhoan_Menu() throws RemoteException{
        int lc;

        NhanVienDAOImpl nvDAO = new NhanVienDAOImpl();
        TaiKhoanDAOImpl taiKhoanDAOImpl = new TaiKhoanDAOImpl();
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xóa");
            System.out.println("4. Xem full danh sách");
            System.out.println("5. Tìm theo mã");
            System.out.println("0. Thoát");

            lc = sc.nextInt();

            switch (lc){
                case 1:
                    sc.nextLine();
                    String maTK = "TK" + faker.number().digits(5);

                    System.out.print("Nhập tên đăng nhập: ");
                    String tenDangNhap = sc.nextLine();

                    System.out.print("Nhập mật khẩu: ");
                    String matKhau = sc.nextLine();

                    String trangThai = "DangHoatDong";

                    System.out.print("Nhập mã nhân viên: ");
                    String maNhanVien = sc.nextLine();

                    TaiKhoanServiceImpl taiKhoanService = new TaiKhoanServiceImpl();
                    NhanVien nhanVien = nvDAO.getNhanVienById(maNhanVien);
                    if (nhanVien != null) {
                        TaiKhoan taiKhoan = new TaiKhoan();
                        taiKhoan.setMaTaiKhoan(maTK);
                        taiKhoan.setTenDangNhap(tenDangNhap);
                        taiKhoan.setMatKhau(matKhau);
                        taiKhoan.setTrangThai(trangThai);
                        taiKhoan.setNhanVien(nhanVien);
                        if (taiKhoanService.insertTaiKhoan(taiKhoan)) {
                            System.out.println("Thêm tài khoản thành công!");
                        } else {
                            System.out.println("Thêm tài khoản thất bại!");
                        }
                    } else {
                        System.out.println("Nhân viên không tồn tại với mã: " + maNhanVien);
                    }
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Nhập mã tài khoản cần sửa: ");
                    String maTaiKhoan = sc.nextLine();
                    TaiKhoan taiKhoan = taiKhoanDAOImpl.getTaiKhoanById(maTaiKhoan);
                    if(taiKhoan == null){
                        System.out.println("Tài khoản không tồn tại.");
                        return;
                    }

                    System.out.print("Nhập tên đăng nhập cần cập nhật (bỏ trống để giữ nguyên): ");
                    String tenDangNhapCapNhat = sc.nextLine();
                    if (!tenDangNhapCapNhat.isEmpty()) {
                        taiKhoan.setTenDangNhap(tenDangNhapCapNhat);
                    }

                    System.out.print("Nhập mật khẩu cần cập nhật (bỏ trống để giữ nguyên): ");
                    String matKhauCapNhat = sc.nextLine();
                    if (!matKhauCapNhat.isEmpty()) {
                        taiKhoan.setMatKhau(matKhauCapNhat);
                    }

                    System.out.print("Nhập trạng thái cần cập nhật(DangHoatDong/DungHoatDong) (bỏ trống để giữ nguyên): ");
                    String trangThaiCapNhat = sc.nextLine();
                    if(!trangThaiCapNhat.isEmpty()){
                        taiKhoan.setTrangThai(trangThaiCapNhat);
                    }

                    if (taiKhoanDAOImpl.updateTaiKhoan(taiKhoan)) {
                        System.out.println("Cập nhật tài khoản thành công!");
                    } else {
                        System.out.println("Cập nhật tài khoản thất bại!");
                    }
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Xóa tài khoản:");
                    System.out.print("Nhập mã tài khoản cần xóa: ");
                    String maTaiKhoanXoa = sc.nextLine();

                    if (taiKhoanDAOImpl.deleteTaiKhoan(maTaiKhoanXoa)) {
                        System.out.println("Xóa tài khoản thành công!");
                    } else {
                        System.out.println("Xóa tài khoản thất bại! Kiểm tra lại mã tài khoản.");
                    }
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("Danh sách tài khoản:");
                    List<TaiKhoan> taiKhoans = taiKhoanDAOImpl.getAllTaiKhoan();
                    if(taiKhoans.isEmpty()){
                        System.out.println("Không có nhân viên nào.");
                    } else{
                        taiKhoans.forEach(tk -> System.out.println(tk));
                    }
                    break;
                case 5:
                    sc.nextLine();
                    System.out.print("Nhập mã tài khoản cần tìm: ");
                    String maTKTim = sc.nextLine();
                    TaiKhoan taiKhoanTim = taiKhoanDAOImpl.getTaiKhoanById(maTKTim);

                    if (taiKhoanTim != null) {
                        System.out.println(taiKhoanTim);
                    } else {
                        System.out.println("Không tìm thấy tài khoản với mã: " + maTKTim);
                    }
                    break;
                case 0:
                    sc.nextLine();
                    break;
                default:
                    sc.nextLine();
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;

            }

        }while (lc != 0);
    }

    public static void DanhMucSanPham_Menu() throws RemoteException {
        int lc;
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xóa");
            System.out.println("4. Xem full danh sách");
            System.out.println("5. Tìm theo mã");
            System.out.println("0. Thoát");

            DanhMucSanPhamDAOImpl danhMucSanPhamDAOImpl = new DanhMucSanPhamDAOImpl();
            lc = sc.nextInt();

            switch (lc){
                case 1:{
                    sc.nextLine();
                    System.out.println("Nhập tên danh mục sản phẩm: ");
                    String tenDanhMuc = sc.nextLine();
                    String maDanhMuc = "DMSP" + faker.number().digits(5);
                    DanhMucSanPham danhMucSanPham = new DanhMucSanPham(maDanhMuc, tenDanhMuc, new ArrayList<>());
                    boolean isThem = danhMucSanPhamDAOImpl.save(danhMucSanPham);
                    if(isThem){
                        System.out.println("Thêm danh mục sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi thêm !!!!");
                    break;
                }
                case 2:{
                    sc.nextLine();
                    System.out.println("Nhập mã danh mục sản phẩm cần cập nhật: ");
                    String maDanhMuc = sc.nextLine();
                    System.out.println("Nhập tên danh mục sản phẩm cần cập nhật: ");
                    String tenDanhMuc = sc.nextLine();
                    DanhMucSanPham danhMucSanPham = new DanhMucSanPham(maDanhMuc, tenDanhMuc, new ArrayList<>());
                    boolean isSua = danhMucSanPhamDAOImpl.update(danhMucSanPham);
                    if(isSua){
                        System.out.println("Sửa danh mục sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi sửa !!!!");
                    break;
                }
                case 3:{
                    sc.nextLine();
                    System.out.println("Nhập mã danh mục sản phẩm cần xóa: ");
                    String maDanhMuc = sc.nextLine();

                    boolean isXoa = danhMucSanPhamDAOImpl.delete(maDanhMuc);
                    if(isXoa){
                        System.out.println("Xóa danh mục sản phẩm thành công !!!");
                    } else System.out.println("Thất bại khi xóa !!!!");
                    break;
                }
                case 4: {
                    danhMucSanPhamDAOImpl.getList().forEach(x -> System.out.println(x));
                    break;
                }
                case 5:{
                    sc.nextLine();
                    System.out.println("Nhập mã danh mục sản phẩm cần tìm: ");
                    String maDanhMuc = sc.nextLine();
                    DanhMucSanPham danhMucSanPham = danhMucSanPhamDAOImpl.findOne(maDanhMuc);
                    if(danhMucSanPham != null){
                        System.out.println(danhMucSanPham);
                    } else System.out.println("Không tìm thấy sản phẩm này !!!!");
                    break;
                }

            }

        }while (lc != 0);
    }

    public static void KhachHang_Menu() throws RemoteException {
        int lc;
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xem full danh sách");
            System.out.println("4. Tìm theo mã");
            System.out.println("5. Xóa khách hàng theo mã");
            System.out.println("0. Thoát");

            lc = sc.nextInt();
            KhachHangDAOImpl khachHangDAOImpl = new KhachHangDAOImpl();
            switch (lc){
                case 1:
                {
                    sc.nextLine();
                    int soluongkhachhang = khachHangDAOImpl.findAll().size();
                    int nextID = Integer.parseInt(khachHangDAOImpl.findAll().get(soluongkhachhang-1).getMaKhachHang().split("KH")[1])+1;
                    String IDformat5kytu = String.format("%05d", nextID);
                    String maKhachHang = "KH" + IDformat5kytu;
                    System.out.println("Hãy nhập tên của khách hàng");
                    String tenKhachHang = sc.nextLine();
                    System.out.println("Hãy số điện thoại 11 chữ số");
                    String sdt = sc.nextLine();
                    System.out.println("Chọn giới tính 0 là Nam và 1 là Nữ và 2 là Khác");
                    int indexGT = sc.nextInt();
                    sc.nextLine();
                    GioiTinh gt = GioiTinh.values()[indexGT];
                    int diemTichLuy = 0;
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKhachHang);
                    khachHang.setSoDienThoai(sdt);
                    khachHang.setTenKhachHang(tenKhachHang);
                    khachHang.setDiemTichLuy(diemTichLuy);
                    khachHang.setGioiTinh(gt);
                    if(khachHangDAOImpl.themKhachHang(khachHang)){
                        System.out.println("Thêm khách hàng thành công");
                    }else{
                        System.out.println("Thêm khách hàng thất bại");
                    }

                    break;
                }
                case 2:{
                    sc.nextLine();
                    System.out.println("Nhập mã khách hảng cần sửa");
                    String maKhachHang = sc.nextLine();
                    KhachHang khachHangCanSua = khachHangDAOImpl.findById(maKhachHang);
                    if(khachHangCanSua == null){
                        System.out.println("Không tìm thấy khách hàng");
                        continue;
                    }
                    System.out.println("Nhập tên để sửa, không nhập tức không sửa");
                    String tenKhachHang = sc.nextLine();
                    if(!tenKhachHang.isEmpty()){
                        khachHangCanSua.setTenKhachHang(tenKhachHang);
                    }
                    System.out.println("Nhập số điện thoại để sửa, không nhập tức không sửa");
                    String sdt = sc.nextLine();
                    if(!sdt.isEmpty()){
                        khachHangCanSua.setSoDienThoai(sdt);
                    }
                    System.out.println("Nhập số giới tính để sửa 0 Nam 1 Nữ 2 Khác , không nhập tức không sửa");
                    String gioiTinh = sc.nextLine();
                    if(!gioiTinh.isEmpty()){
                        int gioiTinhIndex = Integer.parseInt(gioiTinh);
                        GioiTinh gt = GioiTinh.values()[gioiTinhIndex];
                        khachHangCanSua.setGioiTinh(gt);
                    }
                   if( khachHangDAOImpl.suaKhachHang(maKhachHang,khachHangCanSua)){
                       System.out.println("Sửa thông tin khách hàng thành công");
                   }else{
                       System.out.println("Sửa thông tin khách hàng thất bại");
                   }

                    break;

                }
                case 3:{
                    sc.nextLine();
                    List<KhachHang> ds = khachHangDAOImpl.findAll();
                    if(!ds.isEmpty()){
                        for(KhachHang khachHang : ds){
                            System.out.println(khachHang.getMaKhachHang()+ " "+ khachHang.getTenKhachHang() + " " + khachHang.getSoDienThoai());
                        }
                    }else{
                        System.out.println("Không có khách hàng");
                    }
                    break;
                }
                case 4:{
                    sc.nextLine();
                    System.out.println("Nhập mã khách hàng cần tìm");
                    String maKhachHang = sc.nextLine();
                    KhachHang khachHang = khachHangDAOImpl.findById(maKhachHang);
                    if(khachHang == null){
                        System.out.println("Không tìm thấy khách hàng");
                    }else{
                        System.out.println(khachHang.getMaKhachHang()+" "+khachHang.getTenKhachHang()+" "+khachHang.getSoDienThoai());
                    }
                    break;
                }
                case 5:{
                    sc.nextLine();
                    System.out.println("Nhập mã khách hàng cần xóa");
                    String maKhachHang = sc.nextLine();
                    if(khachHangDAOImpl.delete(maKhachHang)){
                        System.out.println("Xóa thành công");
                    }else{
                        System.out.println("Xóa thất bại");
                    }
                    break;
                }
            }


        }while (lc != 0);
    }

    public static void NhanVien_Menu() throws RemoteException{
        int lc;

        NhanVienDAOImpl nhanVienDAOImpl = new NhanVienDAOImpl();
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xóa");
            System.out.println("4. Xem full danh sách");
            System.out.println("5. Tìm theo mã");
            System.out.println("0. Thoát");

            lc = sc.nextInt();

            switch (lc) {
                case 1:
                    sc.nextLine();
                    String maNV = "NV" + faker.number().digits(5);
                    System.out.print("Nhập tên nhân viên: ");
                    String tenNV = sc.nextLine();
                    System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
                    LocalDate ngaySinh = LocalDate.parse(sc.nextLine());
                    System.out.print("Nhập số điện thoại: ");
                    String sdt = sc.nextLine();
                    System.out.print("Nhập địa chỉ: ");
                    String diaChi = sc.nextLine();
                    System.out.print("Nhập số định danh: ");
                    String dinhDanh = sc.nextLine();
                    System.out.print("Nhập giới tính (NAM/NU/KHAC): ");
                    GioiTinh gioiTinh = GioiTinh.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Nhập chức vụ (NGUOIQUANLY/NHANVIEN): ");
                    ChucVuNhanVien chucVu = ChucVuNhanVien.valueOf(sc.nextLine().toUpperCase());

                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMaNhanVien(maNV);
                    nhanVien.setTenNhanVien(tenNV);
                    nhanVien.setNgaySinh(ngaySinh);
                    nhanVien.setNgaySinh(ngaySinh);
                    nhanVien.setDiaChi(diaChi);
                    nhanVien.setSoDinhDanh(dinhDanh);
                    nhanVien.setGioiTinh(gioiTinh);
                    nhanVien.setChucVuNhanVien(chucVu);

                    if (nhanVienDAOImpl.insertNhanVien(nhanVien)) {
                        System.out.println("Thêm nhân viên thành công.");
                    } else {
                        System.out.println("Không thể thêm nhân viên vui lòng kiểm tra lại.");
                    }
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Nhập mã nhân viên cần sửa: ");
                    String maNhanVien = sc.nextLine();
                    NhanVien nhanVienSua = nhanVienDAOImpl.getNhanVienById(maNhanVien);

                    if (nhanVienSua == null) {
                        System.out.println("Nhân viên không tồn tại");
                        return;
                    }

                    System.out.print("Nhập tên nhân viên cần cập nhật (bỏ trống để giữ nguyên): ");
                    String tenMoi = sc.nextLine();
                    if (!tenMoi.isEmpty()) nhanVienSua.setTenNhanVien(tenMoi);

                    System.out.print("Nhập ngày sinh cần cập nhật (yyyy-MM-dd, bỏ trống để giữ nguyên): ");
                    String ngaySinhMoi = sc.nextLine();
                    if (!ngaySinhMoi.isEmpty()) nhanVienSua.setNgaySinh(LocalDate.parse(ngaySinhMoi));

                    System.out.print("Nhập số điện thoại cần cập nhật (bỏ trống để giữ nguyên): ");
                    String soDienThoaiMoi = sc.nextLine();
                    if (!soDienThoaiMoi.isEmpty()) nhanVienSua.setSoDienThoai(soDienThoaiMoi);

                    System.out.print("Nhập địa chỉ cần cập nhật (bỏ trống để giữ nguyên): ");
                    String diaChiMoi = sc.nextLine();
                    if (!diaChiMoi.isEmpty()) nhanVienSua.setMaNhanVien(diaChiMoi);

                    System.out.print("Nhập số định danh cần cập nhật (bỏ trống để giữ nguyên): ");
                    String dinhDanhMoi = sc.nextLine();
                    if (!dinhDanhMoi.isEmpty()) nhanVienSua.setSoDinhDanh(dinhDanhMoi);

                    System.out.print("Nhập giới tính cần cập nhật (NAM/NU/KHAC) (bỏ trống để giữ nguyên): ");
                    String inputGioiTinh = sc.nextLine().trim();
                    if (!inputGioiTinh.isEmpty()) {
                        try {
                            GioiTinh gioiTinhMoi = GioiTinh.valueOf(inputGioiTinh.toUpperCase());
                            nhanVienSua.setGioiTinh(gioiTinhMoi);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Giới tính không hợp lệ, giữ nguyên giá trị cũ.");
                        }
                    }

                    System.out.print("Nhập chức vụ cần cập nhật (NGUOIQUANLY/NHANVIEN) (bỏ trống để giữ nguyên): ");
                    String inputChucVu = sc.nextLine().trim();
                    if (!inputChucVu.isEmpty()) {
                        try {
                            ChucVuNhanVien chucVuNhanVien = ChucVuNhanVien.valueOf(inputChucVu.toUpperCase());
                            nhanVienSua.setChucVuNhanVien(chucVuNhanVien);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Chức vụ không hợp lệ, giữ nguyên giá trị cũ.");
                        }
                    }

                    if (nhanVienDAOImpl.updateNhanVien(nhanVienSua)) {
                        System.out.println("Cập nhật nhân viên thành công.");
                    } else {
                        System.out.println("Cập nhật nhân viên thất bại.");
                    }
                    break;
                case 3:
                    sc.nextLine();
                    System.out.print("Nhập mã nhân viên muốn xóa: ");
                    String maNhanVienXoa = sc.nextLine();

                    if (nhanVienDAOImpl.deleteNhanVien(maNhanVienXoa)) {
                        System.out.println("Xóa nhân viên thành công.");
                    } else {
                        System.out.println("Xóa nhân viên thất bại.");
                    }
                    break;
                case 4:
                    sc.nextLine();
                    List<NhanVien> nhanVienList = nhanVienDAOImpl.getAllNhanVien();
                    if (nhanVienList.isEmpty()) {
                        System.out.println("Không có nhân viên nào.");
                    } else {
                        nhanVienList.forEach(nv -> System.out.println(nv));
                    }
                    break;
                case 5:
                    sc.nextLine();
                    System.out.print("Nhập mã nhân viên cần tìm: ");
                    String maNhanVienTim = sc.nextLine();
                    NhanVien nhanVienTim = nhanVienDAOImpl.getNhanVienById(maNhanVienTim);

                    if (nhanVienTim != null) {
                        System.out.println(nhanVienTim);
                    } else {
                        System.out.println("Nhân viên không tồn tại");
                    }
                    break;
                case 0:
                    sc.nextLine();
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    sc.nextLine();
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        }while (lc != 0);
    }

    public static void KhuyenMai_Menu() throws RemoteException {
        int lc;
        KhuyenMaiDAOImpl khuyenMaiDAOImpl = new KhuyenMaiDAOImpl();
        do{
            System.out.println("1. Thêm");
            System.out.println("2. Sửa");
            System.out.println("3. Xóa");
            System.out.println("4. Xem full danh sách");
            System.out.println("5. Tìm theo mã");
            System.out.println("0. Thoát");

            lc = sc.nextInt();

            switch (lc){
                case 1:
                    sc.nextLine();
                    String maKM = "KM" + faker.number().digits(5);

                    System.out.print("Nhập tên khuyến mãi: ");
                    String tenKhuyenMai = sc.nextLine();
                    System.out.print("Nhập số tiền giảm: ");
                    Double tienGiam = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Nhập ngày bắt đầu (yyyy-MM-dd): ");
                    LocalDate ngayBatDau = LocalDate.parse(sc.nextLine());
                    System.out.print("Nhập ngày kết thúc (yyyy-MM-dd): ");
                    LocalDate ngayKetThuc = LocalDate.parse(sc.nextLine());

                    KhuyenMai khuyenMai = new KhuyenMai();
                    khuyenMai.setMaKhuyenMai(maKM);
                    khuyenMai.setTenKhuyenMai(tenKhuyenMai);
                    khuyenMai.setTienGiam(tienGiam);
                    khuyenMai.setNgayBatDau(ngayBatDau);
                    khuyenMai.setNgayKetThuc(ngayKetThuc);

                    if (khuyenMaiDAOImpl.insertKhuyenMai(khuyenMai)) {
                        System.out.println("Thêm khuyến mãi thành công.");
                    } else {
                        System.out.println("Thêm khuyến mãi thất bại.");
                    }
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Nhập mã khuyến mãi cần sửa: ");
                    String maKhuyenMaiSua = sc.nextLine();
                    KhuyenMai khuyemMaiSua = khuyenMaiDAOImpl.getKhuyenMaiById(maKhuyenMaiSua);
                    if(khuyemMaiSua == null){
                        System.out.println("Khuyến mãi không tồn tại");
                        return;
                    }

                    System.out.print("Nhập tên khuyến mãi mới (bỏ trống để giữ nguyên): ");
                    String tenKM = sc.nextLine();
                    if (!tenKM.isEmpty()) khuyemMaiSua.setTenKhuyenMai(tenKM);

                    System.out.print("Nhập số tiền giảm mới (nhập -1 để giữ nguyên): ");
                    double tienGiamSua = sc.nextDouble();
                    sc.nextLine();
                    if (tienGiamSua != -1) khuyemMaiSua.setTienGiam(tienGiamSua);

                    System.out.print("Nhập ngày bắt đầu mới (yyyy-MM-dd, bỏ trống để giữ nguyên): ");
                    String ngayBatDauSua = sc.nextLine();
                    if (!ngayBatDauSua.isEmpty()) khuyemMaiSua.setNgayBatDau(LocalDate.parse(ngayBatDauSua));

                    System.out.print("Nhập ngày kết thúc mới (yyyy-MM-dd, bỏ trống để giữ nguyên): ");
                    String ngayKetThucSua = sc.nextLine();
                    if (!ngayKetThucSua.isEmpty()) khuyemMaiSua.setNgayKetThuc(LocalDate.parse(ngayKetThucSua));

                    if (khuyenMaiDAOImpl.updateKhuyenMai(khuyemMaiSua)) {
                        System.out.println("Sửa khuyến mãi thành công.");
                    } else {
                        System.out.println("Sửa khuyến mãi thất bại.");
                    }
                    break;
                case 3:
                    sc.nextLine();
                    System.out.print("Nhập mã khuyến mãi cần xóa: ");
                    String maKMXoa = sc.nextLine();
                    if (khuyenMaiDAOImpl.deleteKhuyenMai(maKMXoa)) {
                        System.out.println("Xóa khuyến mãi thành công.");
                    } else {
                        System.out.println("Không thể xóa khuyến mãi.");
                    }
                    break;
                case 4:
                    sc.nextLine();
                    List<KhuyenMai> khuyenMais = khuyenMaiDAOImpl.getAllKhuyenMai();
                    if (!khuyenMais.isEmpty()) {
                        System.out.println("=== DANH SÁCH KHUYẾN MÃI ===");
                        khuyenMais.forEach(km -> System.out.println(km));
                    } else {
                        System.out.println("Không có khuyến mãi nào.");
                    }
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("Nhập mã khuyến mãi muốn tìm");
                    String maKhuyenMaiTimId = sc.nextLine();
                    KhuyenMai khuyemMaiTimId = khuyenMaiDAOImpl.getKhuyenMaiById(maKhuyenMaiTimId);
                    if(khuyemMaiTimId == null){
                        System.out.println("Khuyến mãi không tồn tại");
                        return;
                    } else {
                        System.out.println(khuyemMaiTimId);
                    }
                    break;
                case 0:
                    sc.nextLine();
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    sc.nextLine();
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }

        }while (lc != 0);
    }

    public static void HoaDon_Menu() throws RemoteException {
        int lc;
        do {
            System.out.println("1. Thêm hóa đơn");
            System.out.println("2. Xóa hóa đơn");
            System.out.println("3. Sửa hóa đơn");
            System.out.println("4. Lấy danh sách chi tiết hóa đơn theo mã");
            System.out.println("5. Xem danh sách hóa đơn");
            System.out.println("6. Tìm hóa đơn theo mã");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");
            lc = sc.nextInt();

            KhachHangDAOImpl khachHangDAOImpl = new KhachHangDAOImpl();
            NhanVienDAOImpl nhanVienDAOImpl = new NhanVienDAOImpl();
            HoaDonDAOImpl hoaDonDAOImpl = new HoaDonDAOImpl();
            ChiTietHoaDonDAOImpl chiTietHoaDonDAOImpl = new ChiTietHoaDonDAOImpl();
            SanPhamDAOImpl sanPhamDAOImpl = new SanPhamDAOImpl();

            switch (lc) {
                case 1: {
                    sc.nextLine();
                    System.out.println("Danh sách nhân viên:");
                    List<NhanVien> dsnv = nhanVienDAOImpl.getAllNhanVien();
                    for (NhanVien nv : dsnv) {
                        System.out.println(nv.getMaNhanVien() + " " + nv.getTenNhanVien() + " " + nv.getSoDienThoai());
                    }
                    System.out.print("Nhập mã nhân viên: ");
                    String maNhanVien = sc.nextLine();
                    NhanVien nv = nhanVienDAOImpl.getNhanVienById(maNhanVien);

                    System.out.println("Danh sách khách hàng:");
                    List<KhachHang> dskh = khachHangDAOImpl.findAll();
                    for (KhachHang kh : dskh) {
                        System.out.println(kh.getMaKhachHang() + " " + kh.getTenKhachHang()+ kh.getSoDienThoai());
                    }
                    System.out.print("Nhập mã khách hàng: ");
                    String maKhachHang = sc.nextLine();
                    KhachHang kh = khachHangDAOImpl.findById(maKhachHang);
                    int diemTichLuy = kh.getDiemTichLuy();

                    System.out.printf("Điểm tích lũy của khách hàng là: %d",diemTichLuy);

                    System.out.print("Nhập điểm tích lũy sử dụng: ");
                    int diemTichLuySuDung = sc.nextInt();
                    sc.nextLine();
                    while (diemTichLuySuDung > diemTichLuy) {
                        System.out.println("Nhập lại:");
                        diemTichLuySuDung = sc.nextInt();
                        sc.nextLine();
                    };
                    System.out.print("Nhập ghi chú cho hóa đơn: ");
                    String ghiChu = sc.nextLine();

                    String maHoaDon ="HD" + (String.format("%05d",Integer.parseInt(hoaDonDAOImpl.findAll().get(hoaDonDAOImpl.findAll().size()-1).getMaHoaDon().replace("HD",""))+1)) ;

                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaHoaDon(maHoaDon);
                    hoaDon.setNgayLapHoaDon(LocalDate.now());
                    hoaDon.setKhachHang(kh);
                    hoaDon.setDiemTichLuySuDung(diemTichLuySuDung);
                    hoaDon.setNhanVien(nv);
                    hoaDon.setGhiChu(ghiChu);
                    List<SanPham> dssp = sanPhamDAOImpl.getList();
                    for (SanPham sanPham : dssp) {
                        System.out.println(sanPham.getMaSanPham()+" "+ sanPham.getTenSanPham());
                    }
                    List<ChiTietHoaDon> dscthd = new ArrayList<>();
                    int lc2=0;
                    do {
                        System.out.println("Nhập mã sản phẩm");
                        String maSanPham = sc.nextLine();
                        SanPham sp = sanPhamDAOImpl.findOne(maSanPham);
                        while(sp==null){
                            System.out.println("Không tìm thấy sản phẩm");
                            System.out.println("Nhập lại mã sản phẩm");
                            String maSanPham2 = sc.nextLine();
                            sp = sanPhamDAOImpl.findOne(maSanPham2);
                        }
                        System.out.println("Nhập số lượng");
                        int soLuong = sc.nextInt();
                        sc.nextLine();
                        ChiTietHoaDon ct = new ChiTietHoaDon(soLuong,sp.getGiaBan(),hoaDon,sanPhamDAOImpl.findOne(maSanPham));
                        dscthd.add(ct);
                        System.out.println("Nhấn 1 để nhập tiếp và nhấn 0 để dừng");
                        lc2 = sc.nextInt();
                        sc.nextLine();
                    }while(lc2!=0);

                    hoaDon.setChiTietHoaDons(dscthd);




                    if (hoaDonDAOImpl.lapHoaDon(hoaDon)) {
                        System.out.println("Thêm hóa đơn thành công!");
                    } else {
                        System.out.println("Thêm hóa đơn thất bại!");
                    }
                    break;
                }

                case 2: {
                    sc.nextLine();
                    System.out.print("Nhập mã hóa đơn cần xóa: ");
                    String maHoaDon = sc.nextLine();
                    boolean result = hoaDonDAOImpl.delete(maHoaDon);
                    if (result) {
                        System.out.println("Xóa hóa đơn thành công!");
                    } else {
                        System.out.println("Không tìm thấy mã hóa đơn!");
                    }
                    break;
                }

                case 3: {
                    sc.nextLine();
                    System.out.print("Nhập mã hóa đơn cần sửa: ");
                    String maHoaDon = sc.nextLine();
                    HoaDon hoaDon = hoaDonDAOImpl.findById(maHoaDon);
                    if (hoaDon != null) {

                        System.out.println("Nhập ghi chú mới (không nhập nếu không muốn sửa):");
                        String ghiChu = sc.nextLine();
                        if (!ghiChu.isEmpty()) {
                            hoaDon.setGhiChu(ghiChu);
                        }

                        System.out.println("Nhập điểm tích lũy mới (không nhập nếu không muốn sửa):");
                        String diemTichLuyStr = sc.nextLine();
                        if (!diemTichLuyStr.isEmpty()) {
                            try {
                                int diemTichLuySuDung = Integer.parseInt(diemTichLuyStr);
                                hoaDon.setDiemTichLuySuDung(diemTichLuySuDung);
//                                hoaDon.setTongTien();
                            } catch (NumberFormatException e) {
                                System.out.println("Điểm tích lũy không hợp lệ! Không sửa điểm tích lũy.");
                            }
                        }

                        System.out.println("Nhập mã nhân viên mới (không nhập nếu không muốn sửa):");
                        String maNhanVien = sc.nextLine();
                        if (!maNhanVien.isEmpty()) {
                            NhanVien nhanVien = nhanVienDAOImpl.getNhanVienById(maNhanVien);
                            if (nhanVien != null) {
                                hoaDon.setNhanVien(nhanVien);
                            } else {
                                System.out.println("Mã nhân viên không tồn tại! Không sửa nhân viên.");
                            }
                        }
                        System.out.println("Nhập mã khách hàng mới (không nhập nếu không muốn sửa):");
                        String maKhachHang = sc.nextLine();
                        if (!maKhachHang.isEmpty()) {
                            KhachHang khachHang = khachHangDAOImpl.findById(maKhachHang);
                            if (khachHang != null) {
                                hoaDon.setKhachHang(khachHang);
                            } else {
                                System.out.println("Mã khách hàng không tồn tại! Không sửa khách hàng.");
                            }
                        }
                        boolean result = hoaDonDAOImpl.update(hoaDon);
                        if (result) {
                            System.out.println("Cập nhật hóa đơn thành công!");
                        } else {
                            System.out.println("Cập nhật hóa đơn thất bại!");
                        }
                    } else {
                        System.out.println("Không tìm thấy mã hóa đơn!");
                    }
                    break;
                }

                case 4: {
                    sc.nextLine();
                    System.out.print("Nhập mã hóa đơn: ");
                    String maHoaDon = sc.nextLine();
                    List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDonDAOImpl.getListByHDID(maHoaDon);
                    if (!chiTietHoaDons.isEmpty()) {
                        System.out.println("Chi tiết hóa đơn:");
                        for (ChiTietHoaDon cthd : chiTietHoaDons) {
                            System.out.println(cthd.getSanPham().getMaSanPham() + " " + cthd.getSanPham().getTenSanPham()+ " "+ cthd.getDonGia() +"số lượng: "+ cthd.getSoLuong());
                        }
                    } else {
                        System.out.println("Không tìm thấy chi tiết hóa đơn!");
                    }
                    break;
                }

                case 5: {
                    sc.nextLine();
                    List<HoaDon> hoaDons = hoaDonDAOImpl.findAll();
                    if (!hoaDons.isEmpty()) {
                        System.out.println("Danh sách hóa đơn:");
                        for (HoaDon hd : hoaDons) {
                            System.out.println(hd.toString());
                        }
                    } else {
                        System.out.println("Không có hóa đơn!");
                    }
                    break;
                }

                case 6: {
                    sc.nextLine();
                    System.out.print("Nhập mã hóa đơn cần tìm: ");
                    String maHoaDon = sc.nextLine();
                    HoaDon hoaDon = hoaDonDAOImpl.findById(maHoaDon);
                    if (hoaDon != null) {
                        System.out.println("Thông tin hóa đơn:");
                        System.out.println(hoaDon.toString());
                    } else {
                        System.out.println("Không tìm thấy mã hóa đơn!");
                    }
                    break;
                }

                case 0: {
                    System.out.println("Thoát chương trình!");
                    break;
                }

                default: {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    break;
                }
            }
        } while (lc != 0);
    }
}