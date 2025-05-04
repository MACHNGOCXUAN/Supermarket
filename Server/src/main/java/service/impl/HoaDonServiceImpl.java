package service.impl;

import dao.HoaDonDAO;
import dao.impl.HoaDonDAOImpl;
import model.HoaDon;
import service.HoaDonService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HoaDonServiceImpl extends UnicastRemoteObject implements HoaDonService {
    private final HoaDonDAO hoaDonDAO;

    public HoaDonServiceImpl() throws RemoteException {
        this.hoaDonDAO = new HoaDonDAOImpl();
    }

    @Override
    public boolean lapHoaDon(HoaDon hoaDon) throws RemoteException {
        return hoaDonDAO.lapHoaDon(hoaDon);
    }
    @Override
    public List<HoaDon> findAll() throws RemoteException {
        return hoaDonDAO.findAll();
    }
    @Override
    public HoaDon findById(String id) throws RemoteException {
        return hoaDonDAO.findById(id);
    }
    @Override
    public boolean delete(String id) throws RemoteException {
       return hoaDonDAO.delete(id);
    }
    @Override
    public boolean update(HoaDon hoaDon) throws RemoteException {
      return hoaDonDAO.update(hoaDon);
    }


    @Override
    public Double getTongTien(String id) throws RemoteException {
        return hoaDonDAO.getTongTien(id);
    }
}
