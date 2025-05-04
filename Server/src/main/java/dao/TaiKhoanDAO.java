package dao;

import model.NhanVien;
import model.TaiKhoan;

import java.util.List;

public interface TaiKhoanDAO{
    boolean insertTaiKhoan(TaiKhoan taiKhoan);

    boolean updateTaiKhoan(TaiKhoan taiKhoan);

    boolean deleteTaiKhoan(String maTaiKhoan);

    List<TaiKhoan> getAllTaiKhoan();

    TaiKhoan getTaiKhoanById(String maTaiKhoan);

    List<NhanVien> getNhanVien();
    List<NhanVien> getNhanVienDaCoTaiKhoan();
    List<NhanVien> getNhanVienChuaCoTaiKhoan();



    NhanVien getNhanVienByTaiKhoan(String maTaiKhoan);

    TaiKhoan getTaiKhoanByUsername(String username);
    TaiKhoan verifyTaiKhoan(String username, String password);

    NhanVien getUserByPhone(String phone);

    boolean resetPassword(String maTaiKhoan, String newpassword);

    TaiKhoan taiKhoanByManv(String maTaiKhoan);
}
