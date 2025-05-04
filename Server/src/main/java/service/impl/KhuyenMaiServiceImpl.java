package service.impl;

import dao.KhuyenMaiDAO;
import dao.impl.KhuyenMaiDAOImpl;
import model.KhuyenMai;
import service.KhuyenMaiService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class KhuyenMaiServiceImpl extends UnicastRemoteObject implements KhuyenMaiService {
    private final KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiServiceImpl() throws RemoteException {
        this.khuyenMaiDAO = new KhuyenMaiDAOImpl();
    }

    @Override
    public boolean insertKhuyenMai(KhuyenMai khuyenMai) throws RemoteException{
        return khuyenMaiDAO.insertKhuyenMai(khuyenMai);
    }

    @Override
    public boolean updateKhuyenMai(KhuyenMai khuyenMai) throws RemoteException {
        return khuyenMaiDAO.updateKhuyenMai(khuyenMai);
    }


    @Override
    public boolean deleteKhuyenMai(String maKhuyenMai) throws RemoteException{
       return khuyenMaiDAO.deleteKhuyenMai(maKhuyenMai);
    }

    @Override
    public List<KhuyenMai> getAllKhuyenMai() throws RemoteException {
        return khuyenMaiDAO.getAllKhuyenMai();
    }

    @Override
    public KhuyenMai getKhuyenMaiById(String maKhuyenMai) throws RemoteException {
        return khuyenMaiDAO.getKhuyenMaiById(maKhuyenMai);

    }

    @Override
    public KhuyenMai getKhuyenMaiBySanPhamId(String maSanPham) throws RemoteException {
        return khuyenMaiDAO.getKhuyenMaiBySanPhamId(maSanPham);
    }

    @Override
    public List<KhuyenMai> getDanhSachKhuyenMaiHetHan() throws RemoteException {
        return khuyenMaiDAO.getDanhSachKhuyenMaiHetHan();
    }
}
