package dao;

import model.ChiTietHoaDon;
import model.SanPham;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

public interface SanPhamDAO{
    boolean save(SanPham sanPham);

    SanPham findOne(String id);

    List<SanPham> getList();

    List<SanPham> getListByCategory(String id);

    boolean update(SanPham sanPham);

    boolean delete(String id);

    List<SanPham> getListByFitter(String danhMuc, String month, String year, String status);

    Map<String, Integer> thongKeSoLuongTheoTrangThai();

    double getTongTienNhapHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc);
    double getTongTienNhapHangTheoNam(int year);

    double getTongTienNhapHangTheoThangVaNam(int month, int year);

    List<SanPham> getDanhSachSanPhamSapHetHan();

    boolean capNhatSoLuongSanPham(List<ChiTietHoaDon> chiTietHoaDons);


    boolean saveSanPhamVaThuocTinh(SanPham sanPham);
}
