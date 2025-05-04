package service.impl;

import dao.KhachHangDAO;
import dao.impl.KhachHangDAOImpl;
import model.KhachHang;
import service.KhachHangService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class KhachHangServiceImpl extends UnicastRemoteObject implements KhachHangService {
    private final KhachHangDAO khachHangDAO;

    public KhachHangServiceImpl() throws RemoteException {
        this.khachHangDAO = new KhachHangDAOImpl();
    }

    @Override
    public boolean themKhachHang(KhachHang khachHang) throws RemoteException{
       return khachHangDAO.themKhachHang(khachHang);
    }
    @Override
    public boolean suaKhachHang(String maKhachHang, KhachHang thongTinMoi) throws RemoteException {
      return khachHangDAO.suaKhachHang(maKhachHang, thongTinMoi);
    }
    @Override
    public boolean capNhatDiemTichLuy(String maKhachHang, int diemTichLuyDuocCong) throws RemoteException{
       return khachHangDAO.capNhatDiemTichLuy(maKhachHang, diemTichLuyDuocCong);
    }
    @Override
    public boolean delete(String maKhachHang) throws RemoteException{
        return khachHangDAO.delete(maKhachHang);
    }
    @Override
    public List<KhachHang> findAll() throws RemoteException{
       return khachHangDAO.findAll();
    }
    @Override
    public KhachHang findById(String id) throws RemoteException{
       return khachHangDAO.findById(id);
    }

    @Override
    public KhachHang findBySoDienThoai(String soDienThoai) throws RemoteException {
        return khachHangDAO.findBySoDienThoai(soDienThoai);
    }


}
