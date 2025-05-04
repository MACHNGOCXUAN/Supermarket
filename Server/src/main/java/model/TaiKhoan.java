package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "taikhoans",
        indexes = {
                @Index(name = "idx_ma_tai_khoan", columnList = "ma_tai_khoan")
        }
)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TaiKhoan implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ma_tai_khoan", columnDefinition = "varchar(50)", nullable = false)
    private String maTaiKhoan; @Column(name = "ten_dang_nhap", unique = true)
    private String tenDangNhap;

    @Column(name = "mat_khau")
    private String matKhau;
    @Column(name = "trang_thai")
    private String trangThai;

    @OneToOne
    @JoinColumn(name = "ma_nhan_vien", unique = true)
    private NhanVien nhanVien;

    public TaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau, String trangThai) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }
}
