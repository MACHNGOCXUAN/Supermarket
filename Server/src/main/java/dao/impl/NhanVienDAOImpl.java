package dao.impl;

import dao.NhanVienDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.NhanVien;
import utils.GenerateID;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NhanVienDAOImpl implements NhanVienDAO {

    private static EntityManager em;

    public NhanVienDAOImpl(){
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean insertNhanVien(NhanVien nhanVien) {
        EntityTransaction tr =em.getTransaction();
        nhanVien.setMaNhanVien(
                GenerateID.generateSimpleID(
                        "NV",
                        "nhanviens",
                        "ma_nhan_vien",
                        JPAUtil.getEntityManager())
        );
        try {
            tr.begin();
            em.persist(nhanVien);
            tr.commit();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean updateNhanVien(NhanVien nhanVien){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(nhanVien);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean deleteNhanVien(String maNhanVien) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            NhanVien nhanVien = em.find(NhanVien.class, maNhanVien);

            if (nhanVien != null) {
                em.remove(nhanVien);
                tr.commit();
                return true;
            } else {
                System.out.println("Nhân viên không tồn tại.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public List<NhanVien> getAllNhanVien(){
        try {
            em.clear();  // Clear cache trước
            return em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public NhanVien getNhanVienById(String maNhanVien) {
        try {
            return em.find(NhanVien.class, maNhanVien);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
