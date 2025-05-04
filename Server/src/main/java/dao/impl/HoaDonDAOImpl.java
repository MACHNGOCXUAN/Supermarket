package dao.impl;

import dao.HoaDonDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.SanPham;
import utils.GenerateID;
import utils.JPAUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAOImpl implements HoaDonDAO {
    private static EntityManager em;

    public HoaDonDAOImpl(){
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public synchronized boolean lapHoaDon(HoaDon hoaDon) {
        EntityTransaction et = em.getTransaction();
        hoaDon.setMaHoaDon(
                GenerateID.generateSimpleID(
                        "HD",
                        "hoadons",
                        "ma_hoa_don",
                        JPAUtil.getEntityManager())
        );

        try {
            et.begin();
            // Kiểm tra số lượng tồn
            for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDons()) {
                SanPham sp = em.find(SanPham.class, chiTiet.getSanPham().getMaSanPham());
                em.refresh(sp);  // Ép Hibernate đọc lại bản mới nhất từ Database
                if (sp == null) {
                    et.rollback();
                    return false;
                }

                if (sp.getSoLuongTon() < chiTiet.getSoLuong()) {
                    et.rollback();
                    return false;
                }
            }

            // Merge hóa đơn
            HoaDon mergedHoaDon = em.merge(hoaDon);

            for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDons()) {
                SanPham sp = em.find(SanPham.class, chiTiet.getSanPham().getMaSanPham());
                em.refresh(sp);  // Ép Hibernate đọc lại bản mới nhất từ Database
                sp.setSoLuongTon(sp.getSoLuongTon() - chiTiet.getSoLuong()); // Trừ tồn kho
                em.merge(sp);

                chiTiet.setHoaDon(mergedHoaDon);
                chiTiet.setSanPham(sp); // đã merge rồi
                em.merge(chiTiet);
            }

            et.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }

        return false;
    }




    @Override
    public List<HoaDon> findAll() {
        EntityTransaction et = em.getTransaction();
        List<HoaDon> hoaDons = new ArrayList<>();
        try {
            et.begin();
            em.clear();  // Clear cache trước
            hoaDons = em.createQuery("SELECT hd FROM HoaDon hd", HoaDon.class).getResultList();
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return hoaDons;
    }
    @Override
    public HoaDon findById(String id) {
        EntityTransaction et = em.getTransaction();
        HoaDon hoaDon = null;
        try {
            et.begin();
            hoaDon = em.find(HoaDon.class, id);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return hoaDon;
    }
    @Override
    public boolean delete(String id) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            HoaDon hoaDon = em.find(HoaDon.class, id);
            if (hoaDon != null) {
                List<ChiTietHoaDon> dsct = hoaDon.getChiTietHoaDons();
                for (ChiTietHoaDon chiTiet : dsct) {
                    em.remove(chiTiet);
                }
                em.remove(hoaDon);
                et.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return false;
    }
    @Override
    public boolean update(HoaDon hoaDon) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(hoaDon);
            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (et.isActive()) et.rollback();
        }
        return false;
    }


    @Override
    public Double getTongTien(String id) {
        return em.createQuery("select sum(cth.thanhTien) from ChiTietHoaDon cth where cth.hoaDon.maHoaDon = :id", Double.class)
                .setParameter("id", id)
                .getSingleResult();

    }

    public static void main(String[] args) {
        HoaDonDAOImpl hoaDonDAO = new HoaDonDAOImpl();
        SanPhamDAOImpl sanPhamDAO = new SanPhamDAOImpl();
        SanPham sanPham = sanPhamDAO.findOne("SP24612");

        HoaDon hoaDon = new HoaDon("HD99321", LocalDate.now(), 1000, 25000, "s");
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(2, 12000, hoaDon,  sanPham);
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
        chiTietHoaDons.add(chiTietHoaDon);
        hoaDon.setChiTietHoaDons(chiTietHoaDons);

        hoaDonDAO.lapHoaDon(hoaDon);
    }

}
