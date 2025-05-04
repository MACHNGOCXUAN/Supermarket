package service.impl;

import dao.ThuocTinhSanPhamDAO;
import dao.impl.ThuocTinhSanPhamDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.ThuocTinhSanPham;
import service.ThuocTinhSanPhamService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ThuocTinhSanPhamServiceImpl extends UnicastRemoteObject implements ThuocTinhSanPhamService {
    private final ThuocTinhSanPhamDAO thuocTinhSanPhamDAO;

    public ThuocTinhSanPhamServiceImpl() throws RemoteException {
        this.thuocTinhSanPhamDAO = new ThuocTinhSanPhamDAOImpl();
    }

    @Override
    public List<ThuocTinhSanPham> getListByProductId(String id) throws RemoteException{
        return thuocTinhSanPhamDAO.getListByProductId(id);
    }


    @Override
    public boolean update(ThuocTinhSanPham thuocTinhSanPham) throws RemoteException{
       return thuocTinhSanPhamDAO.update(thuocTinhSanPham);
    }

    @Override
    public boolean save(ThuocTinhSanPham thuocTinhSanPham) throws RemoteException {
        return thuocTinhSanPhamDAO.save(thuocTinhSanPham);
    }

    @Override
    public boolean delete(String id) throws RemoteException{
        return thuocTinhSanPhamDAO.delete(id);
    }

    @Override
    public String findMaxID() throws RemoteException {
        return thuocTinhSanPhamDAO.findMaxID();
    }
}
