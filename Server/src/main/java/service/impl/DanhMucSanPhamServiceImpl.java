package service.impl;

import dao.DanhMucSanPhamDAO;
import dao.impl.DanhMucSanPhamDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.DanhMucSanPham;
import service.DanhMucSanPhamService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class DanhMucSanPhamServiceImpl extends UnicastRemoteObject implements DanhMucSanPhamService {
    private final DanhMucSanPhamDAO danhMucSanPhamDAO;

    public DanhMucSanPhamServiceImpl() throws RemoteException {
        this.danhMucSanPhamDAO = new DanhMucSanPhamDAOImpl();
    }

    @Override
    public boolean save(DanhMucSanPham danhMucSanPham) throws RemoteException{
        return danhMucSanPhamDAO.save(danhMucSanPham);
    }

    @Override
    public DanhMucSanPham findOne(String id) throws RemoteException{
       return danhMucSanPhamDAO.findOne(id);
    }

    @Override
    public List<DanhMucSanPham> getList() throws RemoteException{
        return danhMucSanPhamDAO.getList();
    }

    @Override
    public boolean update(DanhMucSanPham danhMucSanPham) throws RemoteException{
       return danhMucSanPhamDAO.update(danhMucSanPham);
    }

    @Override
    public boolean delete(String id) throws RemoteException{
      return danhMucSanPhamDAO.delete(id);
    }
}
