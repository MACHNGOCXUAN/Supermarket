package service;

import model.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienService extends Remote {
    boolean insertNhanVien(NhanVien nhanVien) throws RemoteException;

    boolean updateNhanVien(NhanVien nhanVien) throws RemoteException;

    boolean deleteNhanVien(String maNhanVien) throws RemoteException;

    List<NhanVien> getAllNhanVien() throws RemoteException;

    NhanVien getNhanVienById(String maNhanVien) throws RemoteException;
}
