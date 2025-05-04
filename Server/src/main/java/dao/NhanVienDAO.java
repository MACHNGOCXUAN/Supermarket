package dao;

import model.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienDAO {
    boolean insertNhanVien(NhanVien nhanVien);

    boolean updateNhanVien(NhanVien nhanVien);

    boolean deleteNhanVien(String maNhanVien);

    List<NhanVien> getAllNhanVien();

    NhanVien getNhanVienById(String maNhanVien);
}
