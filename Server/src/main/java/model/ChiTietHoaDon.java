package model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "chitiethoadons",
        indexes = {
                @Index(name = "idxct_ma_hoa_don", columnList = "ma_hoa_don"),
                @Index(name = "idxct_ma_san_pham", columnList = "ma_san_pham")
        }
)
@NoArgsConstructor
@Data
@Getter
@Setter
@IdClass(ChiTietHoaDon.ChiTietHoaDonId.class)
public class ChiTietHoaDon implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", insertable = false, updatable = false)
    private HoaDon hoaDon;

    @Id
    @ManyToOne
    @JoinColumn(name = "ma_san_pham", insertable = false, updatable = false)
    private SanPham sanPham;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "don_gia")
    private double donGia;

    @Column(name = "thanh_tien")
    private double thanhTien;

    public ChiTietHoaDon(int soLuong, double donGia, HoaDon hoaDon, SanPham sanPham) {
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.setThanhTien();
    }

    public void setThanhTien() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChiTietHoaDonId implements Serializable {
        private HoaDon hoaDon;
        private SanPham sanPham;
    }
}
