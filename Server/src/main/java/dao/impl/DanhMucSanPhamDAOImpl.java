package dao.impl;

import dao.DanhMucSanPhamDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.DanhMucSanPham;
import utils.GenerateID;
import utils.JPAUtil;
import java.util.List;


public class DanhMucSanPhamDAOImpl implements DanhMucSanPhamDAO {
    private static EntityManager em;

    public DanhMucSanPhamDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean save(DanhMucSanPham danhMucSanPham){
        EntityTransaction tr = em.getTransaction();
        danhMucSanPham.setMaDanhMucSanPham(
                GenerateID.generateSimpleID(
                        "DMSP",
                        "danhmucsanphams",
                        "ma_danh_muc_san_pham",
                        JPAUtil.getEntityManager())
        );
        try{
            tr.begin();
            em.persist(danhMucSanPham);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }

        return false;
    }

    @Override
    public DanhMucSanPham findOne(String id){
        DanhMucSanPham danhMucSanPham = em.find(DanhMucSanPham.class, id);
        return danhMucSanPham;
    }

    @Override
    public List<DanhMucSanPham> getList(){
        String query = "select dmsp from DanhMucSanPham dmsp ";
        em.clear();  // Clear cache trước
        return em.createQuery(query, DanhMucSanPham.class)
                .getResultList();
    }

    @Override
    public boolean update(DanhMucSanPham danhMucSanPham){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(danhMucSanPham);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(String id){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            DanhMucSanPham danhMucSanPham = em.find(DanhMucSanPham.class, id);
            em.remove(danhMucSanPham);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }
}
