package service;

import model.NhanVien;
import model.TaiKhoan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaiKhoanService extends Remote {
    boolean insertTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    boolean updateTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    boolean deleteTaiKhoan(String maTaiKhoan) throws RemoteException;

    List<TaiKhoan> getAllTaiKhoan() throws RemoteException;

    TaiKhoan getTaiKhoanById(String maTaiKhoan) throws RemoteException;

    NhanVien getNhanVienByTaiKhoan(String maTaiKhoan) throws RemoteException;

    List<NhanVien> getNhanVienDaCoTaiKhoan() throws RemoteException;
    List<NhanVien> getNhanVienChuaCoTaiKhoan() throws RemoteException;

    List<NhanVien> getNhanVien() throws RemoteException;

    TaiKhoan verifyTaiKhoan(String username, String password) throws RemoteException;

    NhanVien getUserByPhone(String phone) throws RemoteException;

    boolean resetPassword(String maTaiKhoan, String newPassword) throws RemoteException;

    TaiKhoan taiKhoanByManv(String manv) throws RemoteException;
}
