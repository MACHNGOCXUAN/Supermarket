package service;

import model.HoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HoaDonService extends Remote {
    boolean lapHoaDon(HoaDon hoaDon) throws RemoteException;

    List<HoaDon> findAll() throws RemoteException;

    HoaDon findById(String id) throws RemoteException;

    boolean delete(String id) throws RemoteException;

    boolean update(HoaDon hoaDon) throws RemoteException;


    Double getTongTien(String id) throws RemoteException;

}
