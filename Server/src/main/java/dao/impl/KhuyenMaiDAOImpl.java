package dao.impl;

import dao.KhuyenMaiDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.KhuyenMai;
import model.SanPham;
import utils.GenerateID;
import utils.JPAUtil;

import java.time.LocalDate;
import java.util.List;


public class KhuyenMaiDAOImpl implements KhuyenMaiDAO {
    private static EntityManager em;

    public KhuyenMaiDAOImpl(){
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean insertKhuyenMai(KhuyenMai khuyenMai){
        EntityTransaction tr = em.getTransaction();
        khuyenMai.setMaKhuyenMai(
                GenerateID.generateSimpleID(
                        "KM",
                        "khuyenmais",
                        "ma_khuyen_mai",
                        JPAUtil.getEntityManager())
        );
        try {
            tr.begin();
            em.persist(khuyenMai);
            tr.commit();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean updateKhuyenMai(KhuyenMai khuyenMai) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            KhuyenMai existingKhuyenMai = em.find(KhuyenMai.class, khuyenMai.getMaKhuyenMai());

            if (existingKhuyenMai != null) {
                existingKhuyenMai.setTenKhuyenMai(khuyenMai.getTenKhuyenMai());
                existingKhuyenMai.setTienGiam(khuyenMai.getTienGiam());
                existingKhuyenMai.setNgayBatDau(khuyenMai.getNgayBatDau());
                existingKhuyenMai.setNgayKetThuc(khuyenMai.getNgayKetThuc());

                tr.commit();
                return true;
            } else {
                tr.rollback();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }


    @Override
    public boolean deleteKhuyenMai(String maKhuyenMai){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            KhuyenMai khuyenMai = em.find(KhuyenMai.class, maKhuyenMai);
            SanPham sp = khuyenMai.getSanPham();
            // Ngắt liên kết
            if (sp != null) {
                sp.setKhuyenMai(null);
                khuyenMai.setSanPham(null);
            }
            khuyenMai.setSanPham(null);

            em.remove(khuyenMai);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public List<KhuyenMai> getAllKhuyenMai() {
        try {
            em.clear();  // Clear cache trước
            return em.createQuery("SELECT km FROM KhuyenMai km", KhuyenMai.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public KhuyenMai getKhuyenMaiById(String maKhuyenMai) {
        try {
            // Sử dụng em.find() để tìm kiếm chương trình khuyến mãi theo mã
            return em.find(KhuyenMai.class, maKhuyenMai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public KhuyenMai getKhuyenMaiBySanPhamId(String maSanPham)  {
        List<KhuyenMai> results=  em.createQuery(
                        "select km from KhuyenMai km " +
                                "where km.sanPham.maSanPham = :maSanPham " +
                                "and km.ngayBatDau <= current_date " +
                                "and km.ngayKetThuc >= current_date " +
                                "order by km.ngayKetThuc desc", KhuyenMai.class)
                .setParameter("maSanPham", maSanPham)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<KhuyenMai> getDanhSachKhuyenMaiHetHan() {
        List<KhuyenMai> results=  em.createQuery(
                        "select km from KhuyenMai km " +
                                "where km.ngayKetThuc <= current_date ", KhuyenMai.class)
                .getResultList();

        return results.isEmpty() ? null : results;
    }


}
