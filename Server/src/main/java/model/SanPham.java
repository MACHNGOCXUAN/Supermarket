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
import java.util.List;

@Data
@Entity
@Table(
        name = "sanphams",
        indexes = {
                @Index(name = "idx_ma_san_pham", columnList = "ma_san_pham"),
                @Index(name = "idx_ngay_nhap", columnList = "ngay_nhap"),
                @Index(name = "idx_trang_thai", columnList = "trang_thai"),
                @Index(name = "idxsp_ma_danh_muc_san_pham", columnList = "ma_danh_muc_san_pham")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class SanPham implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ma_san_pham", columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String maSanPham;
    @Column(name = "ten_san_pham", columnDefinition = "varchar(100)", unique = true, nullable = false)
    private String tenSanPham;
    @Column(name = "han_su_dung", columnDefinition = "datetime")
    private LocalDate hanSuDung;
    @Column(name = "gia_ban")
    private double giaBan;
    @Column(name = "thue_VAT")
    private double thueVAT;
    @Column(name = "trang_thai", columnDefinition = "varchar(20)", nullable = false)
    private String trangThai;
    @Column(name = "so_luong_ton", columnDefinition = "int", nullable = false)
    private int soLuongTon;
    @Column(name = "ngay_nhap", columnDefinition = "datetime", nullable = false)
    private LocalDate ngayNhap;


    @Column(name = "mo_ta", columnDefinition = "varchar(200)")
    private String moTa;


    @ManyToOne
    @JoinColumn(name = "ma_danh_muc_san_pham")
    private DanhMucSanPham danhMucSanPham;


    @ToString.Exclude
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ChiTietHoaDon> chiTietHoaDons;

    @ToString.Exclude
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  List<ThuocTinhSanPham> thuocTinhSanPhams;

    @ToString.Exclude
    @OneToOne(mappedBy = "sanPham", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KhuyenMai khuyenMai;

    public SanPham(String maSanPham, String tenSanPham, LocalDate hanSuDung, double giaBan, double thueVAT, String trangThai, int soLuongTon, LocalDate ngayNhap, String moTa) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.hanSuDung = hanSuDung;
        this.giaBan = giaBan;
        this.thueVAT = thueVAT;
        this.trangThai = trangThai;
        this.soLuongTon = soLuongTon;
        this.ngayNhap = ngayNhap;
        this.moTa = moTa;
    }
}
