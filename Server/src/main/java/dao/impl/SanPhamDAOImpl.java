package dao.impl;

import dao.SanPhamDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.ChiTietHoaDon;
import model.SanPham;
import model.ThuocTinhSanPham;
import utils.GenerateID;
import utils.JPAUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SanPhamDAOImpl implements SanPhamDAO {
    private static EntityManager em;

    public SanPhamDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean save(SanPham sanPham){
        EntityTransaction tr = em.getTransaction();
        sanPham.setMaSanPham(
                GenerateID.generateSimpleID(
                        "SP",
                        "sanphams",
                        "ma_san_pham",
                        JPAUtil.getEntityManager())
        );
        try{
            tr.begin();
            em.persist(sanPham);
            em.flush(); // Ép JPA ghi xuống DB ngay, đảm bảo sanPham có ID và được công nhận
            em.refresh(sanPham);
            tr.commit();
            return  true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public SanPham findOne(String id) {
        SanPham sanPham = em.find(SanPham.class, id);
        return sanPham;
    }

    @Override
    public List<SanPham> getList() {
        em.clear();  // Clear cache trước
        String query = "select sp from SanPham sp ";
        return em.createQuery(query, SanPham.class)
                .getResultList();


    }

    @Override
    public List<SanPham> getListByFitter(String danhMuc, String month, String year, String status){
        StringBuilder jpql = new StringBuilder("SELECT sp FROM SanPham sp WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (!"Tất cả".equals(danhMuc)) {
            jpql.append(" AND sp.danhMucSanPham.maDanhMucSanPham = :maDanhMuc");
            params.put("maDanhMuc", danhMuc.split("_", 2)[0]);
        }

        if (!"Tất cả".equals(month)) {
            jpql.append(" AND FUNCTION('MONTH', sp.ngayNhap) = :month");
            params.put("month", Integer.parseInt(month));
        }

        if (!"Tất cả".equals(year)) {
            jpql.append(" AND FUNCTION('YEAR', sp.ngayNhap) = :year");
            params.put("year", Integer.parseInt(year));
        }

        if (!"Tất cả".equals(status)) {
            jpql.append(" AND sp.trangThai = :status");
            params.put("status", status);
        }

        TypedQuery<SanPham> query = em.createQuery(jpql.toString(), SanPham.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<SanPham> getListByCategory(String id){
        String query = "select sp from SanPham sp " +
                "where sp.danhMucSanPham.maDanhMucSanPham = :maDanhMuc";
        return em.createQuery(query, SanPham.class)
                .setParameter("maDanhMuc", id)
                .getResultList();
    }

    @Override
    public boolean update(SanPham sanPham){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(sanPham);
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
    public boolean delete(String id){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            SanPham sanPham = em.find(SanPham.class, id);
            em.remove(sanPham);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public Map<String, Integer> thongKeSoLuongTheoTrangThai() {
        String sql = "Select s.trangThai, COUNT(s) from SanPham s GROUP BY s.trangThai";
        return em.createQuery(sql, Object[].class)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                   record -> (String) record[0],
                   record -> ((Long) record[1]).intValue()
                ));
    }

    @Override
    public double getTongTienNhapHangTheoNam(int year) {
        String sql = "select SUM(s.giaBan * s.soLuongTon) from SanPham s " +
                "where YEAR(s.ngayNhap) = :year";
        Double result = em.createQuery(sql, Double.class)
                .setParameter("year", year)
                .getSingleResult();
        return result != null ? result : 0.0; // Trả về 0 nếu result = null
    }

    @Override
    public double getTongTienNhapHangTheoThangVaNam(int month, int year) {
        String sql = "select SUM(s.giaBan * s.soLuongTon) from SanPham s " +
                "where YEAR(s.ngayNhap) = :year AND MONTH(s.ngayNhap) = :month";
        Double result = em.createQuery(sql, Double.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getSingleResult();
        return result != null ? result : 0.0; // Trả về 0 nếu result = null
    }

    @Override
    public double getTongTienNhapHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        String sql = "select SUM(s.giaBan * s.soLuongTon) from SanPham s" +
                " where s.ngayNhap >= :ngayBatDau AND s.ngayNhap <= :ngayKetThuc";
        Double result = em.createQuery(sql, Double.class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getSingleResult();
        return result != null ? result : 0.0; // Trả về 0 nếu result = null
    }

    @Override
    public List<SanPham> getDanhSachSanPhamSapHetHan() {
        String sql = "Select s from SanPham s WHERE s.hanSuDung <= current_date ";
        return em.createQuery(sql, SanPham.class)
                .getResultList();
    }

    @Override
    public boolean capNhatSoLuongSanPham(List<ChiTietHoaDon> chiTietHoaDons) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            SanPhamDAOImpl sanPhamDAO = new SanPhamDAOImpl();
            chiTietHoaDons.stream().forEach(chiTietHoaDon -> {
                SanPham sanPham = sanPhamDAO.findOne(chiTietHoaDon.getSanPham().getMaSanPham());
                sanPham.setSoLuongTon(sanPham.getSoLuongTon() - chiTietHoaDon.getSoLuong());
                sanPhamDAO.update(sanPham);
            });
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }



    @Override
    public boolean saveSanPhamVaThuocTinh(SanPham sanPham) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            // Tạo mã sản phẩm mới
            sanPham.setMaSanPham(
                    GenerateID.generateSimpleID(
                            "SP",
                            "sanphams",
                            "ma_san_pham",
                            em
                    )
            );

            // Lưu SanPham
            SanPham spSaved = em.merge(sanPham);

            // Lưu từng ThuocTinhSanPham
            for (ThuocTinhSanPham thuocTinhSanPham : spSaved.getThuocTinhSanPhams()) {
                thuocTinhSanPham.setSanPham(spSaved);
                em.persist(thuocTinhSanPham);
            }

            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
            return false;
        }
    }



}
