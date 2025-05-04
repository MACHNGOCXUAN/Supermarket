package dao;

import model.ChiTietHoaDon;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ChiTietHoaDonDAO{
    boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon);

    boolean update(ChiTietHoaDon chiTietHoaDon);

    boolean delete(ChiTietHoaDon.ChiTietHoaDonId chiTietHoaDonId);

    ChiTietHoaDon getListCTHDByID(ChiTietHoaDon.ChiTietHoaDonId chiTietHoaDonId);

    List<ChiTietHoaDon> getListByHDID(String hdid);

    Map<LocalDate, Double> doanhThuTheoNgayGanNhat(int limit);

    Map<Integer, Double> thongKeDoanhThuTheoNam(int year);
    Map<LocalDate, Double> thongKeDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc);

    long getSoLuongDonTheoNam(int year);
    long getSoLuongDonTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc);

    long getTongKhachHangTheoNam(int year);

    long getTongKhachHangTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc);
}
