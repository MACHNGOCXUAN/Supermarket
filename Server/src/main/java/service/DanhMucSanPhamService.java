package service;

import model.DanhMucSanPham;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DanhMucSanPhamService extends Remote {
    boolean save(DanhMucSanPham danhMucSanPham) throws RemoteException;

    DanhMucSanPham findOne(String id) throws RemoteException;

    List<DanhMucSanPham> getList() throws RemoteException;

    boolean update(DanhMucSanPham danhMucSanPham) throws RemoteException;

    boolean delete(String id) throws RemoteException;
}
