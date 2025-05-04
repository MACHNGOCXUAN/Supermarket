package dao.impl;

import dao.ThuocTinhSanPhamDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.SanPham;
import model.TaiKhoan;
import model.ThuocTinhSanPham;
import utils.AppUtils;
import utils.GenerateID;
import utils.JPAUtil;

import java.util.List;

public class ThuocTinhSanPhamDAOImpl implements ThuocTinhSanPhamDAO {
    private static EntityManager em;

    public ThuocTinhSanPhamDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public List<ThuocTinhSanPham> getListByProductId(String id){
        String query = "select tt from ThuocTinhSanPham tt "+
                "where sanPham.maSanPham = :maSP";
        return em.createQuery(query, ThuocTinhSanPham.class)
                .setParameter("maSP", id)
                .getResultList();
    }


    @Override
    public boolean update(ThuocTinhSanPham thuocTinhSanPham){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(thuocTinhSanPham);
            em.flush(); // Flush để đảm bảo SanPham được lưu vào DB
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean save(ThuocTinhSanPham thuocTinhSanPham) {
        EntityTransaction tr = em.getTransaction();
        thuocTinhSanPham.setMaThuocTinhSanPham(
                GenerateID.generateSimpleID(
                        "TT",
                        "thuoctinhsanphams",
                        "ma_thuoc_tinh_san_pham",
                        JPAUtil.getEntityManager())
        );
        try{
            tr.begin();
            em.persist(thuocTinhSanPham);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }

        return false;
    }

    @Override
    public String findMaxID(){
        String jpql = "SELECT MAX(ttsp.id) from ThuocTinhSanPham ttsp";
        return  em.createQuery(jpql, String.class)
                .getSingleResult();
    }

    @Override
    public boolean delete(String id){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            ThuocTinhSanPham thuocTinhSanPham = em.find(ThuocTinhSanPham.class, id);
            em.remove(thuocTinhSanPham);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public List<ThuocTinhSanPham> getList() {
        try {
            return em.createQuery("SELECT tt FROM ThuocTinhSanPham tt", ThuocTinhSanPham.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ThuocTinhSanPhamDAOImpl thuocTinhSanPhamDAO = new ThuocTinhSanPhamDAOImpl();
        System.out.println(thuocTinhSanPhamDAO.findMaxID());
    }
}
