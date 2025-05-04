package dao;

import dao.impl.KhachHangDAOImpl;
import dao.impl.TaiKhoanDAOImpl;
import model.GioiTinh;
import model.KhachHang;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KhachHangDAOTEST {
    private static final Logger log = LoggerFactory.getLogger(KhachHangDAOTEST.class);
    private KhachHangDAO khachHangDAO;

    @BeforeAll
    void setup(){
        khachHangDAO = new KhachHangDAOImpl();
    }

    @Test
    void testThemKhachHang() {
        KhachHang testKhachHang = new KhachHang("KH0001", "Xuan", "0909000000", GioiTinh.NAM, 10);
        boolean result = khachHangDAO.themKhachHang(testKhachHang);
        assertTrue(result);

        // Kiểm tra xem khách hàng đã được thêm chưa
        KhachHang kh = khachHangDAO.findById("KH0001");
        assertNotNull(kh);
        assertEquals("Xuan", kh.getTenKhachHang());
    }

    @Test
    void testFindById() {
        KhachHang kh = khachHangDAO.findById("KH0001");
        assertNotNull(kh);
        assertEquals("KH0001", kh.getMaKhachHang());
        assertEquals("Xuan", kh.getTenKhachHang());
    }

    @Test
    void testFindByIdFail() {
        KhachHang khNotExist = khachHangDAO.findById("KH_NOT_EXIST");
        assertNull(khNotExist);
    }


    @Test
    void testFindBySoDienThoai() {
        KhachHang kh = khachHangDAO.findBySoDienThoai("0909000000");
        assertNotNull(kh);
        assertEquals("KH0001", kh.getMaKhachHang());
    }

    @Test
    void testFindBySoDienThoaiFail() {
        // Test với số điện thoại không tồn tại
        assertThrows(Exception.class, () -> {
            khachHangDAO.findBySoDienThoai("0000000000");
        });
    }

    @Test
    void testSuaKhachHang() {
        KhachHang updateInfo = new KhachHang();
        updateInfo.setTenKhachHang("khach hang test");
        updateInfo.setGioiTinh(GioiTinh.NAM);
        updateInfo.setDiemTichLuy(200);

        boolean result = khachHangDAO.suaKhachHang("KH0001", updateInfo);
        assertTrue(result);

        // Kiểm tra thông tin đã được cập nhật
        KhachHang kh = khachHangDAO.findById("KH0001");
        assertEquals("khach hang test", kh.getTenKhachHang());
//        assertEquals("Nam", kh.getGioiTinh());
        assertEquals(200, kh.getDiemTichLuy());

        // Test với khách hàng không tồn tại
        boolean resultNotExist = khachHangDAO.suaKhachHang("KH_NOT_EXIST", updateInfo);
        assertFalse(resultNotExist);
    }


    @Test
    void testCapNhatDiemTichLuy() {
        boolean result = khachHangDAO.capNhatDiemTichLuy("KH0001", 50);
        assertTrue(result);

        // Kiểm tra điểm đã được cộng
        KhachHang kh = khachHangDAO.findById("KH0001");
        assertEquals(250, kh.getDiemTichLuy());

        // Test với khách hàng không tồn tại
        boolean resultNotExist = khachHangDAO.capNhatDiemTichLuy("KH_NOT_EXIST", 50);
        assertFalse(resultNotExist);
    }

    @Test
    void testFindAll() {
        List<KhachHang> dsKhachHang = khachHangDAO.findAll();
        assertNotNull(dsKhachHang);
        assertFalse(dsKhachHang.isEmpty());

        // Kiểm tra xem khách hàng test có trong danh sách không
        boolean found = false;
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKhachHang().equals("KH0001")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testDelete() {
        boolean result = khachHangDAO.delete("KH0001");
        assertTrue(result);

        // Kiểm tra đã xóa chưa
        KhachHang kh = khachHangDAO.findById("KH0001");
        assertNull(kh);

        // Test xóa khách hàng không tồn tại
        boolean resultNotExist = khachHangDAO.delete("KH_NOT_EXIST");
        assertFalse(resultNotExist);
    }




    @AfterAll
    void tearDown(){
        khachHangDAO = null;
    }
}
