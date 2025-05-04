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
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
        name = "nhanviens",
        indexes = {
                @Index(name = "idx_ma_nhan_vien", columnList = "ma_nhan_vien")
        }
)
@NoArgsConstructor
public class NhanVien implements Serializable {

    @Id
    @Column(name = "ma_nhan_vien", columnDefinition = "varchar(50)", nullable = false)
    @EqualsAndHashCode.Include
    private String maNhanVien;
    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    @Column(name = "so_dien_thoai", unique = true)
    private String soDienThoai;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "so_dinh_danh", unique = true)
    private String soDinhDanh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    protected GioiTinh gioiTinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "chuc_vu_nhan_vien")
    protected ChucVuNhanVien chucVuNhanVien;

    @OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<HoaDon> hoaDons;

    public NhanVien(String maNhanVien, String tenNhanVien, LocalDate ngaySinh, String soDienThoai, String diaChi, String soDinhDanh, GioiTinh gioiTinh, ChucVuNhanVien chucVuNhanVien) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.soDinhDanh = soDinhDanh;
        this.gioiTinh = gioiTinh;
        this.chucVuNhanVien = chucVuNhanVien;
    }
}
