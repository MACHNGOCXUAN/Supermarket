package service;

import model.KhuyenMai;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhuyenMaiService extends Remote {
    boolean insertKhuyenMai(KhuyenMai khuyenMai) throws RemoteException;

    boolean updateKhuyenMai(KhuyenMai khuyenMai) throws RemoteException;

    boolean deleteKhuyenMai(String maKhuyenMai) throws RemoteException;

    List<KhuyenMai> getAllKhuyenMai() throws RemoteException;

    KhuyenMai getKhuyenMaiById(String maKhuyenMai) throws RemoteException;

    KhuyenMai getKhuyenMaiBySanPhamId(String maSanPham) throws RemoteException;

    public List<KhuyenMai> getDanhSachKhuyenMaiHetHan() throws RemoteException;
}
