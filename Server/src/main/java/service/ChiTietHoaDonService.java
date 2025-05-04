package service;

import model.ChiTietHoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ChiTietHoaDonService extends Remote {
    boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) throws RemoteException;

    boolean update(ChiTietHoaDon chiTietHoaDon)  throws RemoteException;

    boolean delete(ChiTietHoaDon.ChiTietHoaDonId id) throws RemoteException;

    List<ChiTietHoaDon> getListByHDID(String hdid) throws RemoteException;

    Map<LocalDate, Double> doanhThuTheoNgayGanNhat(int limit) throws RemoteException;

    Map<Integer, Double> thongKeDoanhThuTheoNam(int year) throws RemoteException;

    Map<LocalDate, Double> thongKeDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException;

    long getSoLuongDonTheoNam(int year) throws RemoteException;
    long getSoLuongDonTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException;

    long getTongKhachHangTheoNam(int year) throws RemoteException;

    long getTongKhachHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException;
}
