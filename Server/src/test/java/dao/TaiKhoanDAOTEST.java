package dao;

import dao.impl.TaiKhoanDAOImpl;
import model.TaiKhoan;
import org.junit.jupiter.api.*;
import utils.PasswordUtil;

import java.util.List;

//    boolean insertTaiKhoan(TaiKhoan taiKhoan);
//
//    boolean updateTaiKhoan(TaiKhoan taiKhoan);
//
//    boolean deleteTaiKhoan(String maTaiKhoan);
//
//    List<TaiKhoan> getAllTaiKhoan();
//
//    TaiKhoan getTaiKhoanById(String maTaiKhoan);
//
//    List<NhanVien> getNhanVien();
//    List<NhanVien> getNhanVienDaCoTaiKhoan();
//    List<NhanVien> getNhanVienChuaCoTaiKhoan();
//
//    NhanVien getNhanVienByTaiKhoan(String maTaiKhoan);
//
//    TaiKhoan getTaiKhoanByUsername(String username);

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaiKhoanDAOTEST{
    private TaiKhoanDAO taiKhoanDAO;

    @BeforeAll
    void setup(){
        taiKhoanDAO = new TaiKhoanDAOImpl();
    }

    @Test
    void verifyTaiKhoanTESTSuccess(){
        String username = "bertram.rogahn";
        String password = "123456789";
        TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoanByUsername(username);
        Assertions.assertNotNull(taiKhoan);
        Assertions.assertTrue(PasswordUtil.verifyPassword(password, taiKhoan.getMatKhau()));
    }

    @Test
    void verifyTaiKhoanTESTFailPassword(){
        String username = "bertram.rogahn";
        String password = "12345678912";
        TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoanByUsername(username);
        Assertions.assertNotNull(taiKhoan);
        Assertions.assertFalse(PasswordUtil.verifyPassword(password, taiKhoan.getMatKhau()));
    }

    @Test
    void verifyTaiKhoanTESTFailUsername(){
        String username = "bertrarogahn";
        TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoanByUsername(username);
        Assertions.assertNull(taiKhoan);}


    @AfterAll
    void tearDown(){
        taiKhoanDAO = null;
    }
}
