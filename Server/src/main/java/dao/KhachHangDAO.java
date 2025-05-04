package dao;

import model.KhachHang;

import java.util.List;

public interface KhachHangDAO {
    boolean themKhachHang(KhachHang khachHang);

    boolean suaKhachHang(String maKhachHang, KhachHang thongTinMoi);

    boolean capNhatDiemTichLuy(String maKhachHang, int diemTichLuyDuocCong);

    boolean delete(String maKhachHang);

    List<KhachHang> findAll();

    KhachHang findById(String id);

    KhachHang findBySoDienThoai(String soDienThoai);
}
