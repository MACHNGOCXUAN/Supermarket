package service;

import model.ChiTietHoaDon;
import model.SanPham;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SanPhamService extends Remote {
    boolean save(SanPham sanPham) throws RemoteException;

    SanPham findOne(String id) throws RemoteException;

    List<SanPham> getList() throws RemoteException;

    List<SanPham> getListByFitter(String danhMuc, String month, String year, String status) throws RemoteException;

    List<SanPham> getListByCategory(String id) throws RemoteException;

    boolean update(SanPham sanPham) throws RemoteException;

    boolean delete(String id) throws RemoteException;

    Map<String, Integer> thongKeSoLuongTheoTrangThai() throws RemoteException;

    double getTongTienNhapHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException;
    double getTongTienNhapHangTheoNam(int year) throws RemoteException;

    double getTongTienNhapHangTheoThangVaNam(int month, int year) throws RemoteException;

    List<SanPham> getDanhSachSanPhamSapHetHan() throws RemoteException;

    boolean capNhatSoLuongSanPham(List<ChiTietHoaDon> chiTietHoaDons) throws RemoteException;

    boolean saveSanPhamVaThuocTinh(SanPham sanPham) throws RemoteException;


}
