package dao.impl;

import dao.KhachHangDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.KhachHang;
import utils.GenerateID;
import utils.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAOImpl implements KhachHangDAO {
    private static EntityManager em;

    public KhachHangDAOImpl(){
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean themKhachHang(KhachHang khachHang){
        EntityTransaction tr = em.getTransaction();
        khachHang.setMaKhachHang(
                GenerateID.generateSimpleID(
                        "KH",
                        "khachhangs",
                        "ma_khach_hang",
                        JPAUtil.getEntityManager())
        );
        try {
            tr.begin();
            em.persist(khachHang);
            tr.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    @Override
    public boolean suaKhachHang(String maKhachHang, KhachHang thongTinMoi) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            // Tìm khách hàng hiện tại
            KhachHang khachHangHienTai = em.find(KhachHang.class, maKhachHang);
            if (khachHangHienTai != null) {
                khachHangHienTai.setTenKhachHang(thongTinMoi.getTenKhachHang());
                khachHangHienTai.setGioiTinh(thongTinMoi.getGioiTinh());
                khachHangHienTai.setDiemTichLuy(thongTinMoi.getDiemTichLuy());
                em.merge(khachHangHienTai);
            } else {
                System.out.println("Khách hàng không tồn tại.");
                return false;
            }
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();

        }
        return false;
    }
    @Override
    public boolean capNhatDiemTichLuy(String maKhachHang, int diemTichLuyDuocCong){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            KhachHang khachHang = em.find(KhachHang.class, maKhachHang);
            if (khachHang != null) {
                khachHang.setDiemTichLuy(khachHang.getDiemTichLuy()+diemTichLuyDuocCong);
                em.merge(khachHang);
                tr.commit();
                return true;
            }


        }catch (Exception e){
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    @Override
    public boolean delete(String maKhachHang){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            KhachHang khachHang = em.find(KhachHang.class, maKhachHang);
            em.remove(khachHang);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    @Override
    public List<KhachHang> findAll(){
        EntityTransaction tr = em.getTransaction();
        List<KhachHang> dskh = new ArrayList<>();
        try {
            tr.begin();
            em.clear();  // Clear cache trước
            dskh = em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
                tr.rollback();

        }
        return dskh;
    }
    @Override
    public KhachHang findById(String id){
        EntityTransaction tr = em.getTransaction();
        KhachHang khachHang = null;
        try {
            tr.begin();
            khachHang = em.find(KhachHang.class, id);
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();

        }
        return khachHang;
    }

    @Override
    public KhachHang findBySoDienThoai(String soDienThoai) {
        return em.createQuery("select kh from KhachHang kh where kh.soDienThoai = :soDienThoai", KhachHang.class)
                .setParameter("soDienThoai", soDienThoai)
                .getSingleResult();
    }


}
