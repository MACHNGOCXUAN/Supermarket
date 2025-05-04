package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(
        name = "thuoctinhsanphams",
        indexes = {
                @Index(name = "idx_ma_thuoc_tinh_san_pham", columnList = "ma_thuoc_tinh_san_pham")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ThuocTinhSanPham implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ma_thuoc_tinh_san_pham", columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String maThuocTinhSanPham;
    @Column(name = "ten_thuoc_tinh", columnDefinition = "varchar(80)")
    private String tenThuocTinh;
    @Column(name = "gia_tri_thuoc_tinh", columnDefinition = "varchar(100)")
    private String giaTriThuocTinh;

    public ThuocTinhSanPham(String maThuocTinhSanPham, String tenThuocTinh, String giaTriThuocTinh, SanPham sanPham) {
        this.maThuocTinhSanPham = maThuocTinhSanPham;
        this.tenThuocTinh = tenThuocTinh;
        this.giaTriThuocTinh = giaTriThuocTinh;
        this.sanPham = sanPham;
    }
    public ThuocTinhSanPham(String maThuocTinhSanPham, String tenThuocTinh, String giaTriThuocTinh) {
        this.maThuocTinhSanPham = maThuocTinhSanPham;
        this.tenThuocTinh = tenThuocTinh;
        this.giaTriThuocTinh = giaTriThuocTinh;
    }

    @ManyToOne
    @JoinColumn(name = "ma_san_pham")
    private SanPham sanPham;

}
