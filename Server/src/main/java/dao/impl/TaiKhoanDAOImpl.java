package dao.impl;

import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.NhanVien;
import model.TaiKhoan;
import utils.GenerateID;
import utils.JPAUtil;
import utils.PasswordUtil;

import java.util.List;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {
    private static EntityManager em;

    public TaiKhoanDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public boolean insertTaiKhoan(TaiKhoan taiKhoan) {
        EntityTransaction tr = em.getTransaction();
        taiKhoan.setMaTaiKhoan(
                GenerateID.generateSimpleID(
                        "TK",
                        "taikhoans",
                        "ma_tai_khoan",
                        JPAUtil.getEntityManager())
        );
        try {
            tr.begin();
            em.persist(taiKhoan);
            tr.commit();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean updateTaiKhoan(TaiKhoan taiKhoan){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(taiKhoan);
            tr.commit();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }


//    @Override
//    public boolean deleteTaiKhoan(String maTaiKhoan){
//        EntityTransaction tr = em.getTransaction();
//        try {
//            tr.begin();
//            TaiKhoan taiKhoan = em.find(TaiKhoan.class, maTaiKhoan);
//            if (taiKhoan != null) {
//                em.remove(taiKhoan);  // Xóa tài khoản
//                tr.commit();
//                return true;
//            } else {
//                System.out.println("Tài khoản không tồn tại.");
//            }
//
//        } catch (Exception ex){
//            ex.printStackTrace();
//            tr.rollback();
//        }
//        return false;
//    }

    @Override
    public boolean deleteTaiKhoan(String maTaiKhoan) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TaiKhoan taiKhoan = em.find(TaiKhoan.class, maTaiKhoan);
            if (taiKhoan != null) {
                // Tìm nhân viên đang dùng tài khoản này
                NhanVien nhanVien = em.find(NhanVien.class, taiKhoan.getNhanVien().getMaNhanVien());
                if (nhanVien != null) {
                    nhanVien.setTaiKhoan(null);  // Ngắt liên kết
                    em.merge(nhanVien);          // Cập nhật lại nhân viên
                }
                em.remove(taiKhoan);
                tr.commit();
                return true;
            } else {
                System.out.println("Tài khoản không tồn tại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        }
        return false;
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan(){
        try {
            em.clear();  // Clear cache trước
            return em.createQuery("SELECT tk FROM TaiKhoan tk", TaiKhoan.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public TaiKhoan getTaiKhoanById(String maTaiKhoan){
        try {
            return em.find(TaiKhoan.class, maTaiKhoan);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public List<NhanVien> getNhanVien(){
        String query = "SELECT n FROM NhanVien n JOIN n.taiKhoan tk";
        return em.createQuery(query, NhanVien.class).getResultList();
    }

    @Override
    public List<NhanVien> getNhanVienDaCoTaiKhoan() {
        String query = "SELECT n FROM NhanVien n WHERE n IN (SELECT tk.nhanVien FROM TaiKhoan tk)";
        return em.createQuery(query, NhanVien.class).getResultList();
    }

    @Override
    public List<NhanVien> getNhanVienChuaCoTaiKhoan() {
        String query = "SELECT n FROM NhanVien n WHERE NOT EXISTS (SELECT 1 FROM TaiKhoan tk WHERE tk.nhanVien = n)";
        return em.createQuery(query, NhanVien.class).getResultList();
    }

    @Override
    public NhanVien getNhanVienByTaiKhoan(String maTaiKhoan) {
        return em.createQuery("select tk.nhanVien from TaiKhoan tk where tk.maTaiKhoan = :maTaiKhoan", NhanVien.class)
                .setParameter("maTaiKhoan", maTaiKhoan)
                .getSingleResult();
    }

    @Override
    public TaiKhoan getTaiKhoanByUsername(String username) {
        try {
            String query = "SELECT tk FROM TaiKhoan tk WHERE tk.tenDangNhap = :username";
            return em.createQuery(query, TaiKhoan.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TaiKhoan verifyTaiKhoan(String username, String password){
        try {
            String query = "SELECT tk FROM TaiKhoan tk WHERE tk.tenDangNhap = :username AND tk.matKhau = :password";
            return em.createQuery(query, TaiKhoan.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public NhanVien getUserByPhone(String phone){
        try {
            String query = "SELECT nv FROM NhanVien nv WHERE nv.soDienThoai = :phone";
            return em.createQuery(query, NhanVien.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean resetPassword(String maTaiKhoan, String newPassword) {
        if (maTaiKhoan == null || maTaiKhoan.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            // Tìm tài khoản theo mã
            TaiKhoan taiKhoan = em.find(TaiKhoan.class, maTaiKhoan);
            if (taiKhoan == null) {
                tr.rollback();
                return false;
            }

            // Mã hóa mật khẩu mới trước khi lưu
            String encryptedPassword = PasswordUtil.hashPassword(newPassword);
            taiKhoan.setMatKhau(encryptedPassword);

            em.merge(taiKhoan);
            tr.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public TaiKhoan taiKhoanByManv(String manv) {
        try {
            String query = "SELECT tk FROM TaiKhoan tk WHERE tk.nhanVien.maNhanVien = :manv";
            return em.createQuery(query, TaiKhoan.class)
                    .setParameter("manv", manv)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
