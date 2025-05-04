package data;

import dao.SanPhamDAO;
import dao.impl.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.*;
import net.datafaker.Faker;
import utils.PasswordUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class DataGenerator {
    private final Faker faker = new Faker();
    private final Random rd = new Random();

    public NhanVien generateNhanVien() {
        NhanVien nhanVien = new NhanVien();
        NhanVienDAOImpl nhanVienDAO = new NhanVienDAOImpl();

        nhanVien.setMaNhanVien(String.format("NV%05d", nhanVienDAO.getAllNhanVien().size() + 1));
        nhanVien.setTenNhanVien(faker.name().fullName());
        nhanVien.setNgaySinh(LocalDate.of(
                faker.number().numberBetween(2000, 2025),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28)
        ));
        nhanVien.setSoDienThoai("0" + (3 + faker.random().nextInt(7)) + faker.number().digits(8));
        nhanVien.setDiaChi(faker.address().fullAddress());
        nhanVien.setSoDinhDanh("74"+faker.number().digits(10));
        nhanVien.setGioiTinh(rd.nextBoolean() ? GioiTinh.NAM : GioiTinh.NU);
        int chucvu = faker.random().nextInt(0, 1);
        if(chucvu == 0)
            nhanVien.setChucVuNhanVien(ChucVuNhanVien.NHANVIEN);
        else
            nhanVien.setChucVuNhanVien(ChucVuNhanVien.NGUOIQUANLY);
        nhanVien.setTaiKhoan(new TaiKhoan());
        return nhanVien;
    }

    public TaiKhoan generateTaiKhoan(NhanVien nhanVien) {
        TaiKhoan taiKhoan = new TaiKhoan();
        TaiKhoanDAOImpl taiKhoanDAO = new TaiKhoanDAOImpl();

        taiKhoan.setMaTaiKhoan(String.format("TK%05d", taiKhoanDAO.getAllTaiKhoan().size() + 1));
        taiKhoan.setTenDangNhap(faker.internet().username());
        taiKhoan.setMatKhau("123456789");
        taiKhoan.setTrangThai("hoạt động");
        taiKhoan.setNhanVien(nhanVien);

        return taiKhoan;
    }

    public KhuyenMai generateKhuyenMai(SanPham sanPham, int idKhuyenMai) {
        KhuyenMai khuyenMai = new KhuyenMai();

        khuyenMai.setMaKhuyenMai(String.format("KM%05d", idKhuyenMai));
        khuyenMai.setTenKhuyenMai(faker.commerce().promotionCode());
        khuyenMai.setTienGiam(faker.random().nextDouble(0.05, 0.20));

        LocalDate today = LocalDate.now();
        khuyenMai.setNgayBatDau(today.minusDays(rd.nextInt(30)));
        khuyenMai.setNgayKetThuc(today.plusDays(rd.nextInt(30)));

        khuyenMai.setSanPham(sanPham);

        return khuyenMai;
    }

    public SanPham generateSanPham(DanhMucSanPham danhMucSanPham, int idSanPham, AtomicInteger incIndexTT) {
        SanPham sanPham = new SanPham();

        sanPham.setMaSanPham(String.format("SP%05d", idSanPham));
        sanPham.setTenSanPham(faker.commerce().productName() + "_" + faker.random().nextInt(1, 1000));
        LocalDate hanSuDung = LocalDate.now()
                .plusDays(faker.number().numberBetween(-20, 360));
        sanPham.setHanSuDung(hanSuDung);
        sanPham.setGiaBan(Double.parseDouble(faker.commerce().price(20000, 70000)));
        sanPham.setThueVAT(faker.random().nextDouble(0.01, 0.20));
        int status = faker.random().nextInt(0, 10);

//      Xác suất 10% :)))
        if(status != 0)
            sanPham.setTrangThai("Còn bán");
        else sanPham.setTrangThai("Ngưng bán");

        sanPham.setSoLuongTon(faker.random().nextInt(10, 120));

        LocalDate ngayNhap = LocalDate.now().minusDays(faker.random().nextInt(1, 365 * 3));
        sanPham.setNgayNhap(ngayNhap);

        // Tạo mô tả
        String productDescription = "Sản phẩm: " + faker.commerce().productName() +
                ". Được sản xuất tại " + faker.address().country() +
                " với tiêu chuẩn " + faker.company().name() + ".";
        sanPham.setMoTa(productDescription);


//        List<ChiTietHoaDon> cthd = new ArrayList<>();
//        for (int i = 0; i < faker.random().nextInt(2, 5); i++) {
//            cthd.add(generateChiTietHoaDon());
//        }
//        sanPham.setChiTietHoaDons(cthd);



        List<ThuocTinhSanPham> dsThongTin = new ArrayList<>();

        for (int i = 0; i < faker.random().nextInt(2, 5); i++) {
            ThuocTinhSanPham tt = new ThuocTinhSanPham();
            tt.setMaThuocTinhSanPham(String.format("TT%05d", incIndexTT.getAndIncrement()));

            int rn = faker.random().nextInt(1, 5);
            if(rn == 1){
                tt.setTenThuocTinh("Nguyên liệu");
                tt.setGiaTriThuocTinh(faker.commerce().material());
            } else if (rn == 2){
                tt.setTenThuocTinh("Nhà cung cấp");
                tt.setGiaTriThuocTinh(faker.commerce().vendor());
            } else if (rn == 3){
                tt.setTenThuocTinh("Hàm lượng");
                tt.setGiaTriThuocTinh(faker.random().nextInt(100, 200) + " gam");
            } else if (rn == 4){
                tt.setTenThuocTinh("Calo");
                tt.setGiaTriThuocTinh(faker.random().nextInt(100, 400) + " calo");
            } else {
                tt.setTenThuocTinh("Dung tích");
                tt.setGiaTriThuocTinh(faker.random().nextDouble(0.5, 2.5) + " lít");
            }
            dsThongTin.add(tt);
        }
        sanPham.setThuocTinhSanPhams(dsThongTin);

        sanPham.setDanhMucSanPham(danhMucSanPham);

        return sanPham;
    }

    public DanhMucSanPham generateDanhMucSanPham(){
        DanhMucSanPham danhMucSanPham = new DanhMucSanPham();
        DanhMucSanPhamDAOImpl danhMucSanPhamDAO = new DanhMucSanPhamDAOImpl();
        danhMucSanPham.setMaDanhMucSanPham(String.format("DMSP%05d", danhMucSanPhamDAO.getList().size() + 1));
        danhMucSanPham.setTenDanhMucSanPham(faker.company().name());

        return danhMucSanPham;
    }

    public  KhachHang generateKhachHang() {
            Faker faker = new Faker();
            KhachHangDAOImpl khachHangDAO = new KhachHangDAOImpl();
            KhachHang khachHang = new KhachHang();

            khachHang.setMaKhachHang(String.format("KH%05d", khachHangDAO.findAll().size() + 1));
            khachHang.setTenKhachHang(faker.name().fullName());
            khachHang.setSoDienThoai("0" + (3 + faker.random().nextInt(7)) + faker.number().digits(8));
            int rdGT = faker.random().nextInt(0, 2);
            khachHang.setGioiTinh(GioiTinh.values() [rdGT]);
            khachHang.setDiemTichLuy(faker.number().numberBetween(10000, 10000));

            return khachHang;

    }
    public ChiTietHoaDon generateChiTietHoaDon(HoaDon hoaDon, SanPham sanPham) {

        int soLuong = faker.random().nextInt(3, 25);
        double donGia = faker.number().randomDouble(2, 100000, 300000);

        return new ChiTietHoaDon(soLuong, donGia, hoaDon, sanPham);
    }
    public HoaDon generateHoaDon(NhanVien nhanVien, KhachHang khachHang) {
        Faker faker = new Faker();
        HoaDonDAOImpl hoaDonDAO = new HoaDonDAOImpl();

//        LocalDate startDate = LocalDate.now().minusDays(faker.random().nextLong(1, 365 * 2));
//        LocalDate endDate = LocalDate.now();
//        long randomDays = faker.number().numberBetween(0, ChronoUnit.DAYS.between(startDate, endDate));
        LocalDate ngayLapHoaDon = LocalDate.now().minusDays(faker.random().nextLong(1, 365 * 2));
        String maHoaDon = String.format("HD%05d", hoaDonDAO.findAll().size() + 1);
        int diemTichLuySuDung = faker.random().nextInt(1000, 10000);
        String ghiChu = faker.lorem().sentence(); // Ghi chú ngẫu nhiên

        HoaDon hoaDon = new HoaDon(ngayLapHoaDon, maHoaDon, diemTichLuySuDung, ghiChu, khachHang, nhanVien);

        return hoaDon;
    }




    public void generateAndPrintSampleData() {
        EntityManager em = Persistence
                .createEntityManagerFactory("mariadb")
                .createEntityManager();
        SanPhamDAOImpl sanPhamDAO = new SanPhamDAOImpl();
        KhuyenMaiDAOImpl khuyenMaiDAO = new KhuyenMaiDAOImpl();
        ThuocTinhSanPhamDAOImpl thuocTinhSanPhamDAO = new ThuocTinhSanPhamDAOImpl();
        AtomicInteger incIndexTT = new AtomicInteger(thuocTinhSanPhamDAO.getList().size() + 1); // Khởi tạo mã TT

        EntityTransaction tr = em.getTransaction();
        for (int i = 0; i < 70; i++) {
            NhanVien nhanVien = generateNhanVien();

            TaiKhoan taiKhoan = generateTaiKhoan(nhanVien);
            String hashPassword = PasswordUtil.hashPassword(taiKhoan.getMatKhau());
            taiKhoan.setMatKhau(hashPassword);

            KhachHang khachHang = generateKhachHang();
            HoaDon hoaDon = generateHoaDon(nhanVien, khachHang);
            nhanVien.setTaiKhoan(taiKhoan);
            nhanVien.setTaiKhoan(taiKhoan);

            DanhMucSanPham danhMucSanPham = generateDanhMucSanPham();

            List<SanPham> dssp = new ArrayList<>();
            int idSanPham = sanPhamDAO.getList().size();
            for (int j = 0; j < faker.random().nextInt(2, 15); j++) {
                SanPham sanPham = generateSanPham(danhMucSanPham, idSanPham + 1, incIndexTT);
                idSanPham++;
                dssp.add(sanPham);
            }
            danhMucSanPham.setSanPhams(dssp);

            List<KhuyenMai> dskm = new ArrayList<>();
            int idKhuyenMai = khuyenMaiDAO.getAllKhuyenMai().size();
            for (int j = 0; j < faker.random().nextInt(0, dssp.size()); j++) {
                KhuyenMai khuyenMai = generateKhuyenMai(dssp.get(j), idKhuyenMai + 1);
                idKhuyenMai++;
                dssp.get(j).setKhuyenMai(khuyenMai);
                dskm.add(khuyenMai);
            }
            tr.begin();
            em.persist(nhanVien);
            em.persist(taiKhoan);
            em.persist(khachHang);
            em.persist(danhMucSanPham);
            em.persist(hoaDon);

            for (SanPham sp : dssp){
                em.persist(sp);
                for(ThuocTinhSanPham thuocTinhSanPham : sp.getThuocTinhSanPhams()){
                    thuocTinhSanPham.setSanPham(sp);
                    em.persist(thuocTinhSanPham);
                }
                ChiTietHoaDon chiTietHoaDon = generateChiTietHoaDon(hoaDon,sp);
                em.persist(chiTietHoaDon);
                hoaDon.addChiTietHoaDon(chiTietHoaDon);
            }
            em.merge(hoaDon);

            for (KhuyenMai km : dskm){
                em.persist(km);
            }
            tr.commit();
        }
    }

    public static void main(String[] args) {
        DataGenerator generator = new DataGenerator();
        generator.generateAndPrintSampleData();
    }
}


