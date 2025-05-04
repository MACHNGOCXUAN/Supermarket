package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "khuyenmais",
        indexes = {
                @Index(name = "idx_ma_khuyen_mai", columnList = "ma_khuyen_mai")
        }
)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class KhuyenMai implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ma_khuyen_mai", unique = true, columnDefinition = "varchar(50)", nullable = false)
    private String maKhuyenMai;

    @Column(name = "ten_khuyen_mai")
    private String tenKhuyenMai;

    @Column(name = "tien_giam")
    private Double tienGiam;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;

    @OneToOne
    @JoinColumn(name = "ma_san_pham", unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private SanPham sanPham;


    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, Double tienGiam, LocalDate ngayBatDau, LocalDate ngayKetThuc, SanPham sanPham) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.tienGiam = tienGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.sanPham = sanPham;
    }
    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, Double tienGiam, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.tienGiam = tienGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
}
