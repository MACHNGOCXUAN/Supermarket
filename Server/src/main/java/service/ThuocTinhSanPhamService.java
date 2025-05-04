package service;

import model.ThuocTinhSanPham;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ThuocTinhSanPhamService extends Remote {
    List<ThuocTinhSanPham> getListByProductId(String id) throws RemoteException;

    boolean update(ThuocTinhSanPham thuocTinhSanPham) throws RemoteException;

    boolean save(ThuocTinhSanPham thuocTinhSanPham) throws RemoteException;

    boolean delete(String id) throws RemoteException;


    String findMaxID() throws RemoteException;
}
