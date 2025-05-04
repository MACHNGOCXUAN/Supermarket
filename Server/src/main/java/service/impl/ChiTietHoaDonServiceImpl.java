package service.impl;

import dao.ChiTietHoaDonDAO;
import dao.impl.ChiTietHoaDonDAOImpl;
import model.ChiTietHoaDon;
import service.ChiTietHoaDonService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class ChiTietHoaDonServiceImpl extends UnicastRemoteObject implements ChiTietHoaDonService {
    private final ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ChiTietHoaDonServiceImpl() throws RemoteException {
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAOImpl();
    }

    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon)  throws RemoteException {
        return chiTietHoaDonDAO.themChiTietHoaDon(chiTietHoaDon);
    }

    @Override
    public boolean update(ChiTietHoaDon chiTietHoaDon) throws RemoteException {
        return chiTietHoaDonDAO.update(chiTietHoaDon);
    }

    @Override
    public boolean delete(ChiTietHoaDon.ChiTietHoaDonId id) throws RemoteException {
        return chiTietHoaDonDAO.delete(id);
    }

    @Override
    public List<ChiTietHoaDon> getListByHDID(String hdid) throws RemoteException {
       return chiTietHoaDonDAO.getListByHDID(hdid);
    }

    @Override
    public Map<Integer, Double> thongKeDoanhThuTheoNam(int year) throws RemoteException {
        return chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year);
    }

    @Override
    public Map<LocalDate, Double> doanhThuTheoNgayGanNhat(int limit) throws RemoteException {
        return chiTietHoaDonDAO.doanhThuTheoNgayGanNhat(limit);
    }

    @Override
    public Map<LocalDate, Double> thongKeDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException {
        return chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc);
    }

    @Override
    public long getSoLuongDonTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException {
        return chiTietHoaDonDAO.getSoLuongDonTheoNgay(ngayBatDau, ngayKetThuc);
    }

    @Override
    public long getTongKhachHangTheoNam(int year) throws RemoteException {
        return chiTietHoaDonDAO.getTongKhachHangTheoNam(year);
    }

    @Override
    public long getTongKhachHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException {
        return chiTietHoaDonDAO.getTongKhachHangTheoNgay(ngayBatDau, ngayKetThuc);
    }

    @Override
    public long getSoLuongDonTheoNam(int year) throws RemoteException {
        return chiTietHoaDonDAO.getSoLuongDonTheoNam(year);
    }
}
