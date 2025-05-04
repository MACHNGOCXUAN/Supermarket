package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(
        name = "hoadons",
        indexes = {
                @Index(name = "idx_ma_hoa_don", columnList = "ma_hoa_don"),
                @Index(name = "idxhd_ma_khach_hang", columnList = "ma_khach_hang"),
                @Index(name = "idxhd_ma_nhan_vien", columnList = "ma_nhan_vien")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class HoaDon implements Serializable {
    @Id
    @Column(name = "ma_hoa_don", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private  String maHoaDon;
    @Column(name="ngay_lap_hoa_don")
    private LocalDate ngayLapHoaDon;
    @Column(name="diem_tich_luy_su_dung")
    private int diemTichLuySuDung;
    @Column(name = "tong_tien")
    private double tongTien;
    @Column(name="ghi_chu")
    private String ghiChu;


    @ManyToOne
    @JoinColumn(name = "ma_khach_hang")
    @ToString.Exclude
    private KhachHang khachHang;
    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien")
    @ToString.Exclude
    private NhanVien nhanVien;

    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietHoaDon> chiTietHoaDons;

    public HoaDon(String maHoaDon, LocalDate ngayLapHoaDon, int diemTichLuySuDung, double tongTien, String ghiChu) {
        this.maHoaDon = maHoaDon;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.diemTichLuySuDung = diemTichLuySuDung;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
    }


    private void setTongTien() {
        this.tongTien = this.getChiTietHoaDons().stream()
                .mapToDouble(x -> x.getSoLuong() * x.getDonGia())
                .sum();
    }

    public void setDiemTichLuySuDung(int diemTichLuySuDung) {
        this.diemTichLuySuDung = diemTichLuySuDung;
        this.tongTien = this.tongTien - diemTichLuySuDung;
    }

    public HoaDon(LocalDate ngayLapHoaDon, String maHoaDon, int diemTichLuySuDung, String ghiChu, KhachHang khachHang, NhanVien nhanVien) {
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.maHoaDon = maHoaDon;
        this.diemTichLuySuDung = diemTichLuySuDung;
        this.ghiChu = ghiChu;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.chiTietHoaDons = new ArrayList<>();
        setTongTien();
        this.tongTien -= diemTichLuySuDung;
    }
    public void addChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDons.add(chiTietHoaDon);
        setTongTien();
    }

    public void setChiTietHoaDons(List<ChiTietHoaDon> chiTietHoaDons) {
        this.chiTietHoaDons = chiTietHoaDons;
        setTongTien();
    }
}
