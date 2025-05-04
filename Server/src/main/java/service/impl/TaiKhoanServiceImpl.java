package service.impl;

import dao.TaiKhoanDAO;
import dao.impl.TaiKhoanDAOImpl;
import model.NhanVien;
import model.TaiKhoan;
import service.TaiKhoanService;
import utils.PasswordUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TaiKhoanServiceImpl extends UnicastRemoteObject implements TaiKhoanService {
    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanServiceImpl()  throws RemoteException {
        this.taiKhoanDAO = new TaiKhoanDAOImpl();
    }

    @Override
    public boolean insertTaiKhoan(TaiKhoan taiKhoan) throws RemoteException {
        String hashPassword = PasswordUtil.hashPassword(taiKhoan.getMatKhau());
        taiKhoan.setMatKhau(hashPassword);
      return taiKhoanDAO.insertTaiKhoan(taiKhoan);
    }

    @Override
    public boolean updateTaiKhoan(TaiKhoan taiKhoan)  throws RemoteException{
        String hashPassword = PasswordUtil.hashPassword(taiKhoan.getMatKhau());
        taiKhoan.setMatKhau(hashPassword);
        return taiKhoanDAO.updateTaiKhoan(taiKhoan);
    }


    @Override
    public boolean deleteTaiKhoan(String maTaiKhoan) throws RemoteException{
        return taiKhoanDAO.deleteTaiKhoan(maTaiKhoan);
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() throws RemoteException{
       return taiKhoanDAO.getAllTaiKhoan();
    }

    @Override
    public TaiKhoan getTaiKhoanById(String maTaiKhoan) throws RemoteException{
       return taiKhoanDAO.getTaiKhoanById(maTaiKhoan);
    }

    @Override
    public NhanVien getNhanVienByTaiKhoan(String maTaiKhoan) throws RemoteException {
        return taiKhoanDAO.getNhanVienByTaiKhoan(maTaiKhoan);

    }

    @Override
    public List<NhanVien> getNhanVienDaCoTaiKhoan() throws RemoteException{
        return taiKhoanDAO.getNhanVienDaCoTaiKhoan();
    }

    @Override
    public List<NhanVien> getNhanVienChuaCoTaiKhoan() throws RemoteException{
        return taiKhoanDAO.getNhanVienChuaCoTaiKhoan();
    }

    @Override
    public List<NhanVien> getNhanVien() throws RemoteException{
       return taiKhoanDAO.getNhanVien();
    }

    @Override
    public TaiKhoan verifyTaiKhoan(String username, String password) throws RemoteException{
        TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoanByUsername(username);
        if (taiKhoan != null && PasswordUtil.verifyPassword(password, taiKhoan.getMatKhau())) {
            return taiKhoan;
        }
       return null;
    }

    @Override
    public NhanVien getUserByPhone(String phone) {
        return taiKhoanDAO.getUserByPhone(phone);
    }

    @Override
    public boolean resetPassword(String maTaiKhoan, String newPassword) {
        return taiKhoanDAO.resetPassword(maTaiKhoan, newPassword);
    }

    @Override
    public TaiKhoan taiKhoanByManv(String manv) {
        return  taiKhoanDAO.taiKhoanByManv(manv);
    }
}
