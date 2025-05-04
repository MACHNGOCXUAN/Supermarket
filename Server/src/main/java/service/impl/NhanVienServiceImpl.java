package service.impl;

import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.impl.KhachHangDAOImpl;
import dao.impl.NhanVienDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.NhanVien;
import service.NhanVienService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NhanVienServiceImpl extends UnicastRemoteObject implements NhanVienService {

    private final NhanVienDAO nhanVienDAO;

    public NhanVienServiceImpl() throws RemoteException {
        this.nhanVienDAO = new NhanVienDAOImpl();
    }

    @Override
    public boolean insertNhanVien(NhanVien nhanVien) throws RemoteException{
      return nhanVienDAO.insertNhanVien(nhanVien);
    }

    @Override
    public boolean updateNhanVien(NhanVien nhanVien) throws RemoteException{
        return nhanVienDAO.updateNhanVien(nhanVien);
    }

    @Override
    public boolean deleteNhanVien(String maNhanVien) throws RemoteException{
        return nhanVienDAO.deleteNhanVien(maNhanVien);
    }

    @Override
    public List<NhanVien> getAllNhanVien() throws RemoteException{
       return nhanVienDAO.getAllNhanVien();
    }

    @Override
    public NhanVien getNhanVienById(String maNhanVien) throws RemoteException {
       return nhanVienDAO.getNhanVienById(maNhanVien);
    }
}
