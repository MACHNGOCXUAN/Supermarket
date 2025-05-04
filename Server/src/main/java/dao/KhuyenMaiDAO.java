package dao;

import model.KhuyenMai;

import java.util.List;

public interface KhuyenMaiDAO{
    boolean insertKhuyenMai(KhuyenMai khuyenMai);

    boolean updateKhuyenMai(KhuyenMai khuyenMai);

    boolean deleteKhuyenMai(String maKhuyenMai);

    List<KhuyenMai> getAllKhuyenMai();

    KhuyenMai getKhuyenMaiById(String maKhuyenMai);


    KhuyenMai getKhuyenMaiBySanPhamId(String maSanPham) ;

    List<KhuyenMai> getDanhSachKhuyenMaiHetHan();

}
