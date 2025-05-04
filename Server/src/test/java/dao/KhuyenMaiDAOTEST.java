package dao;

import dao.impl.KhuyenMaiDAOImpl;
import dao.impl.SanPhamDAOImpl;
import model.KhuyenMai;
import model.SanPham;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KhuyenMaiDAOTEST {
    private KhuyenMaiDAO khuyenMaiDAO;
    private SanPhamDAO sanPhamDAO;
    @BeforeEach
    void setUp(){
        khuyenMaiDAO = new KhuyenMaiDAOImpl();
        sanPhamDAO = new SanPhamDAOImpl();
    }

    @Test
    void insertKhuyenMaiTest(){
        KhuyenMai khuyenMaiMoi = new KhuyenMai();
        khuyenMaiMoi.setMaKhuyenMai("KM0001");
        khuyenMaiMoi.setTenKhuyenMai("Khuyến mãi 1");
        khuyenMaiMoi.setTienGiam(100000.0);
        khuyenMaiMoi.setNgayBatDau(LocalDate.of(2023, 10, 1));
        khuyenMaiMoi.setNgayKetThuc(LocalDate.of(2023, 10, 31));
        SanPham sp = sanPhamDAO.findOne("SP99682");
        khuyenMaiMoi.setSanPham(sp);

        boolean result = khuyenMaiDAO.insertKhuyenMai(khuyenMaiMoi);
        assertTrue(result);
        assertNotNull(khuyenMaiDAO.getKhuyenMaiById("KM0001"));
        assertEquals("Khuyến mãi 1", khuyenMaiDAO.getKhuyenMaiById("KM0001").getTenKhuyenMai());
    }

    @Test
    void updateKhuyenMaiTest(){
        String newTenKhuyenMai = "Tên mới nè";
        KhuyenMai khuyenMai = khuyenMaiDAO.getKhuyenMaiById("KM57843");
        khuyenMai.setTenKhuyenMai(newTenKhuyenMai);

        boolean result = khuyenMaiDAO.updateKhuyenMai(khuyenMai);
        assertTrue(result);
        assertEquals(newTenKhuyenMai, khuyenMaiDAO.getKhuyenMaiById("KM57843").getTenKhuyenMai());
    }

    @Test
    void deleteKhuyenMaiTest(){
        boolean result = khuyenMaiDAO.deleteKhuyenMai("KM0001");
        assertTrue(result);
        assertNull(khuyenMaiDAO.getKhuyenMaiById("KM0001"));
    }

    @Test
    void getKhuyenMaiByIdTest(){
        KhuyenMai khuyenMaiNull = khuyenMaiDAO.getKhuyenMaiById("KM0001");
        assertNull(khuyenMaiNull);
        KhuyenMai khuyenMaiKhongNull = khuyenMaiDAO.getKhuyenMaiById("KM03185");
        assertNotNull(khuyenMaiKhongNull);
        assertEquals("PromoIncredible767084", khuyenMaiKhongNull.getTenKhuyenMai());
    }

    @Test
    void getAllKhuyenMaiTest(){
        assertNotNull(khuyenMaiDAO.getAllKhuyenMai());
        assertFalse(khuyenMaiDAO.getAllKhuyenMai().isEmpty());
        assertEquals(14, khuyenMaiDAO.getAllKhuyenMai().size());
        assertEquals("PromoIncredible767084", khuyenMaiDAO.getAllKhuyenMai().getFirst().getTenKhuyenMai());
        assertEquals("PromotionCool285014", khuyenMaiDAO.getAllKhuyenMai().getLast().getTenKhuyenMai());
    }

    @Test
    void getKhuyenMaiBySanPhamIdTest(){
        KhuyenMai khuyenMai = khuyenMaiDAO.getKhuyenMaiBySanPhamId("SP55682");
        assertNotNull(khuyenMai);
        assertEquals("PromoIncredible767084", khuyenMai.getTenKhuyenMai());
    }
    @Test
    void getDanhSachKhuyenMaiHetHanTest(){
        assertNull( khuyenMaiDAO.getDanhSachKhuyenMaiHetHan());
    }

    @AfterEach
    void tearDown(){
        khuyenMaiDAO = null;
        sanPhamDAO = null;
    }
}
