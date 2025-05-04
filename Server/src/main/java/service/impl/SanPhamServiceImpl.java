package service.impl;

import dao.SanPhamDAO;
import dao.impl.SanPhamDAOImpl;
import model.ChiTietHoaDon;
import model.SanPham;
import service.SanPhamService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SanPhamServiceImpl extends UnicastRemoteObject implements SanPhamService {

    private final SanPhamDAO sanPhamDAO;

    public SanPhamServiceImpl() throws RemoteException {
        this.sanPhamDAO = new SanPhamDAOImpl();
    }


    @Override
    public boolean save(SanPham sanPham) throws RemoteException{
        return sanPhamDAO.save(sanPham);
    }


    @Override
    public SanPham findOne(String id) throws RemoteException {
        return sanPhamDAO.findOne(id);
    }


    @Override
    public List<SanPham> getList() throws RemoteException {
       return sanPhamDAO.getList();
    }


    @Override
    public List<SanPham> getListByFitter(String danhMuc, String month, String year, String status) throws RemoteException{
       return sanPhamDAO.getListByFitter(danhMuc, month, year, status);
    }


    @Override
    public List<SanPham> getListByCategory(String id) throws RemoteException{
       return sanPhamDAO.getListByCategory(id);
    }


    @Override
    public boolean update(SanPham sanPham) throws RemoteException{
        return sanPhamDAO.update(sanPham);
    }

    @Override
    public boolean delete(String id) throws RemoteException{
       return sanPhamDAO.delete(id);
    }

    @Override
    public Map<String, Integer> thongKeSoLuongTheoTrangThai() throws RemoteException {
        return sanPhamDAO.thongKeSoLuongTheoTrangThai();
    }

    @Override
    public double getTongTienNhapHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws RemoteException {
        return sanPhamDAO.getTongTienNhapHangTheoNgay(ngayBatDau, ngayKetThuc);
    }

    @Override
    public double getTongTienNhapHangTheoNam(int year) throws RemoteException {
        return sanPhamDAO.getTongTienNhapHangTheoNam(year);
    }

    @Override
    public double getTongTienNhapHangTheoThangVaNam(int month, int year) throws RemoteException {
        return sanPhamDAO.getTongTienNhapHangTheoThangVaNam(month, year);
    }

    @Override
    public List<SanPham> getDanhSachSanPhamSapHetHan() throws RemoteException {
        return sanPhamDAO.getDanhSachSanPhamSapHetHan();
    }

    @Override
    public boolean capNhatSoLuongSanPham(List<ChiTietHoaDon> chiTietHoaDons) throws RemoteException{
        return sanPhamDAO.capNhatSoLuongSanPham(chiTietHoaDons);
    }

    @Override
    public boolean saveSanPhamVaThuocTinh(SanPham sanPham) throws RemoteException {
        return sanPhamDAO.saveSanPhamVaThuocTinh(sanPham);
    }
}
