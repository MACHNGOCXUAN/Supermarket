package dao;

import dao.impl.NhanVienDAOImpl;
import model.ChucVuNhanVien;
import model.GioiTinh;
import model.NhanVien;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NhanVienDAOTEST {
    private NhanVienDAO nhanVienDAO;

    @BeforeAll
    public void beforeAll() {
        nhanVienDAO = new NhanVienDAOImpl();
    }

    @Test
    void testInsertNhanVien() {

        NhanVien nhanVien = new NhanVien(
                "NV0001",
                "Mach Xuan",
                LocalDate.of(2004, 9, 19),
                "0909999999",
                "Thanh Hoa",
                "123456789123",
                GioiTinh.NAM,
                ChucVuNhanVien.NGUOIQUANLY);

        boolean result = nhanVienDAO.insertNhanVien(nhanVien);
        assertTrue(result);

        // Kiểm tra xem nhân viên đã được thêm chưa
        NhanVien nv = nhanVienDAO.getNhanVienById("NV0001");
        assertNotNull(nv);
        assertEquals("Mach Xuan", nv.getTenNhanVien());
    }

    @Test
    void testGetNhanVienById() {
        NhanVien nv = nhanVienDAO.getNhanVienById("NV0001");
        assertNotNull(nv);
        assertEquals("NV0001", nv.getMaNhanVien());
        assertEquals("Mach Xuan", nv.getTenNhanVien());

        // Test tìm không tồn tại
        NhanVien nvNotExist = nhanVienDAO.getNhanVienById("NV_NOT_EXIST");
        assertNull(nvNotExist);
    }

    @Test
    void testUpdateNhanVien() {
        NhanVien nv = nhanVienDAO.getNhanVienById("NV0001");
        assertNotNull(nv);

        // Cập nhật thông tin
        nv.setTenNhanVien("Mach Ngoc Xuan");
        nv.setSoDienThoai("0908989898");

        boolean result = nhanVienDAO.updateNhanVien(nv);
        assertTrue(result);

        // Kiểm tra thông tin đã được cập nhật
        NhanVien updatedNv = nhanVienDAO.getNhanVienById("NV0001");
        assertEquals("Mach Ngoc Xuan", updatedNv.getTenNhanVien());
        assertEquals("0908989898", updatedNv.getSoDienThoai());
    }

    @Test
    void testGetAllNhanVien() {
        List<NhanVien> dsNhanVien = nhanVienDAO.getAllNhanVien();
        assertNotNull(dsNhanVien);
        assertFalse(dsNhanVien.isEmpty());

        // Kiểm tra xem nhân viên test có trong danh sách không
        boolean found = false;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getMaNhanVien().equals("NV0001")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }


    @Test
    void testDeleteNhanVien() {

        boolean result = nhanVienDAO.deleteNhanVien("NV0001");
        assertTrue(result);

        // Kiểm tra đã xóa chưa
        NhanVien nv = nhanVienDAO.getNhanVienById("NV0001");
        assertNull(nv);

        // Test xóa nhân viên không tồn tại
        boolean resultNotExist = nhanVienDAO.deleteNhanVien("NV_NOT_EXIST");
        assertFalse(resultNotExist);
    }

    @AfterAll
    public void afterAll() {
        nhanVienDAO = null;
    }
}
