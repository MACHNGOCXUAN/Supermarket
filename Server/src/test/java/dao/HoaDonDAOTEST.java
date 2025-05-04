package dao;
import dao.impl.HoaDonDAOImpl;
import dao.impl.KhachHangDAOImpl;
import dao.impl.NhanVienDAOImpl;
import dao.impl.SanPhamDAOImpl;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.List;
import static  org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HoaDonDAOTEST {
    private HoaDonDAO hoaDonDAO;
    private KhachHangDAO khachHangDAO;
    private NhanVienDAO nhanVienDAO;
    private SanPhamDAO sanPhamDAO;

    @BeforeEach
    public void setUp() {
        sanPhamDAO = new SanPhamDAOImpl();
        hoaDonDAO = new HoaDonDAOImpl();
        khachHangDAO = new KhachHangDAOImpl();
        nhanVienDAO = new NhanVienDAOImpl();

    }
    @Test
    void lapHoaDonTest(){
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon("HD001");
            hoaDon.setNgayLapHoaDon(LocalDate.now());
            NhanVien nhanVien = nhanVienDAO.getNhanVienById("NV01852");
            KhachHang khachHang = khachHangDAO.findBySoDienThoai("46450551718");
            hoaDon.setGhiChu("");
            hoaDon.setDiemTichLuySuDung(0);
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNhanVien(nhanVien);
        ChiTietHoaDon cthd1 = new ChiTietHoaDon(11, sanPhamDAO.findOne("SP99682").getGiaBan(), hoaDon, sanPhamDAO.findOne("SP99682"));
        List<ChiTietHoaDon> dscthd = List.of(cthd1);
        hoaDon.setChiTietHoaDons(dscthd);

        boolean result = hoaDonDAO.lapHoaDon(hoaDon);
        assertTrue(result);
        assertEquals("NV01852", hoaDonDAO.findById("HD001").getNhanVien().getMaNhanVien());

    }
    @Test
    void getTongTien(){
        assertEquals(4647157.35, hoaDonDAO.getTongTien("HD001"));
    }

    @Test
    void findAllTest(){
        List<HoaDon> dshd = hoaDonDAO.findAll();
        assertEquals(17, dshd.size());
        assertEquals("HD001", dshd.get(0).getMaHoaDon());
    }
    @Test
    void findByIdTest(){
        HoaDon hoaDon = hoaDonDAO.findById("HD001");
        assertEquals("", hoaDon.getGhiChu());
        assertNotNull(hoaDonDAO.findById("HD12710"));
    }
    @Test
    void deleteTest(){
        boolean result = hoaDonDAO.delete("HD001");
        assertTrue(result);
        assertEquals(16, hoaDonDAO.findAll().size());
        assertEquals("HD03585", hoaDonDAO.findAll().getFirst().getMaHoaDon());
    }
    @Test
    void updateTest(){
        HoaDon hd = hoaDonDAO.findById("HD03585");
        hd.setGhiChu("Khach khong lay hoa don");
        hd.setDiemTichLuySuDung(1000);
        boolean result = hoaDonDAO.update(hd);
        assertTrue(result);
        assertEquals(1000, hoaDonDAO.findById("HD03585").getDiemTichLuySuDung());
        assertEquals("Khach khong lay hoa don", hoaDonDAO.findById("HD03585").getGhiChu());
    }


    @AfterEach
    public void tearDown() {
        hoaDonDAO = null;
        khachHangDAO = null;
        nhanVienDAO = null;
        sanPhamDAO = null;
    }

}
