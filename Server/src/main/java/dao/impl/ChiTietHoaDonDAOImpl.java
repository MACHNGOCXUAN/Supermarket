package dao.impl;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.SanPham;
import utils.JPAUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ChiTietHoaDonDAOImpl implements ChiTietHoaDonDAO {
    private static EntityManager em;

    public ChiTietHoaDonDAOImpl(){
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon){
        EntityTransaction et = em.getTransaction();
        try{
            et.begin();
            em.persist(chiTietHoaDon);
            et.commit();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            et.rollback();
        }
        return false;
    }

    @Override
    public boolean update(ChiTietHoaDon chiTietHoaDon){
        EntityTransaction et = em.getTransaction();
        try{
            et.begin();
            em.merge(chiTietHoaDon);
            et.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            et.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(ChiTietHoaDon.ChiTietHoaDonId chiTietHoaDonId){
        EntityTransaction et = em.getTransaction();
        try{
            et.begin();
            em.remove(em.find(ChiTietHoaDon.class, chiTietHoaDonId));
            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return false;
        }

    @Override
    public ChiTietHoaDon getListCTHDByID(ChiTietHoaDon.ChiTietHoaDonId chiTietHoaDonId) {
        ChiTietHoaDon chiTietHoaDon = em.find(ChiTietHoaDon.class, chiTietHoaDonId);
        return chiTietHoaDon;
    }

    @Override
    public List<ChiTietHoaDon> getListByHDID(String hdid){
        EntityTransaction et = em.getTransaction();
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
        try{
            et.begin();
            em.clear();  // Clear cache trước
            chiTietHoaDons = em.createQuery("select cthds from ChiTietHoaDon cthds where cthds.hoaDon.maHoaDon = :hdid").setParameter("hdid", hdid).getResultList();
            et.commit();
        }catch (Exception e){
            e.printStackTrace();
            et.rollback();
        }
        return chiTietHoaDons;
    }

    @Override
    public Map<LocalDate, Double> doanhThuTheoNgayGanNhat(int limit) {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(limit); // Tính từ hôm nay lùi 6 ngày
        String jpql = "SELECT hd.ngayLapHoaDon, ROUND(SUM(cthd.thanhTien), 2) " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "WHERE hd.ngayLapHoaDon >= :sevenDaysAgo " +
                "GROUP BY hd.ngayLapHoaDon " +
                "ORDER BY hd.ngayLapHoaDon DESC";
        return em.createQuery(jpql, Object[].class)
                .setParameter("sevenDaysAgo", sevenDaysAgo)
                .setMaxResults(7)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                   record -> (LocalDate)record[0],
                   record -> (Double)record[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map<Integer, Double> thongKeDoanhThuTheoNam(int year) {
        String jpql = "SELECT MONTH(hd.ngayLapHoaDon), ROUND(SUM(cthd.thanhTien), 2) " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "WHERE YEAR(hd.ngayLapHoaDon) = :year " +
                "GROUP BY MONTH(hd.ngayLapHoaDon) " +
                "ORDER BY MONTH(hd.ngayLapHoaDon) ASC ";
        return em.createQuery(jpql, Object[].class)
                .setParameter("year", year)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        record -> (Integer)record[0],
                        record -> (Double)record[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map<LocalDate, Double> thongKeDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        String jpql = "SELECT hd.ngayLapHoaDon, ROUND(SUM(cthd.thanhTien), 2) " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "WHERE hd.ngayLapHoaDon >= :ngayBatDau AND hd.ngayLapHoaDon <= :ngayKetThuc " +
                "GROUP BY hd.ngayLapHoaDon " +
                "ORDER BY hd.ngayLapHoaDon DESC";
        return em.createQuery(jpql, Object[].class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        record -> (LocalDate)record[0],
                        record -> (Double)record[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }


    @Override
    public long getSoLuongDonTheoNam(int year) {
        String jpql = "SELECT SUM(cthd.soLuong) as tongSoLuong " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "WHERE YEAR(hd.ngayLapHoaDon) = :year";

        Long soLuong = em.createQuery(jpql, Long.class)
                .setParameter("year", year)
                .getSingleResult();

        return soLuong != null ? soLuong : 0;
    }

    @Override
    public long getSoLuongDonTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        String jpql = "SELECT SUM(cthd.soLuong) " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "WHERE hd.ngayLapHoaDon >= :ngayBatDau AND hd.ngayLapHoaDon <= :ngayKetThuc ";
        Long soLuong = em.createQuery(jpql, Long.class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getSingleResult();
        return soLuong != null ? soLuong : 0;
    }

    @Override
    public long getTongKhachHangTheoNam(int year) {
        String jpql = "SELECT COUNT(hd.khachHang) " +
                "FROM HoaDon hd " +
                "WHERE YEAR(hd.ngayLapHoaDon) = :year ";
        Long soLuong =  em.createQuery(jpql, Long.class)
                .setParameter("year", year)
                .getSingleResult();
        return soLuong != null ? soLuong : 0;
    }

    @Override
    public long getTongKhachHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        String jpql = "SELECT COUNT(hd.khachHang) " +
                "FROM HoaDon hd " +
                "WHERE hd.ngayLapHoaDon >= :ngayBatDau AND hd.ngayLapHoaDon <= :ngayKetThuc ";
        Long soLuong =  em.createQuery(jpql, Long.class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getSingleResult();
        return soLuong != null ? soLuong : 0;
    }

    public static void main(String[] args) {
        ChiTietHoaDonDAOImpl chiTietHoaDonDAO = new ChiTietHoaDonDAOImpl();
        HoaDonDAOImpl hoaDonDAO = new HoaDonDAOImpl();
        SanPhamDAOImpl sanPhamDAO = new SanPhamDAOImpl();
        SanPham sanPham = sanPhamDAO.findOne("SP24612");
        HoaDon hoaDon = hoaDonDAO.findById("HD87288");
        System.out.println(chiTietHoaDonDAO.getListCTHDByID(new ChiTietHoaDon.ChiTietHoaDonId(hoaDon, sanPham)));
    }


}
