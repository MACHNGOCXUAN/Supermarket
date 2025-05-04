package service;

import model.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhachHangService extends Remote {
    boolean themKhachHang(KhachHang khachHang) throws RemoteException;

    boolean suaKhachHang(String maKhachHang, KhachHang thongTinMoi) throws RemoteException;

    boolean capNhatDiemTichLuy(String maKhachHang, int diemTichLuyDuocCong) throws RemoteException;

    boolean delete(String maKhachHang) throws RemoteException;

    List<KhachHang> findAll() throws RemoteException;

    KhachHang findById(String id) throws RemoteException;

    KhachHang findBySoDienThoai(String soDienThoai) throws RemoteException;
}
