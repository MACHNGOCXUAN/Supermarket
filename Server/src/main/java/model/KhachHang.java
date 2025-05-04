package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "khachhangs",
        indexes = {
            @Index(name = "idx_ma_khach_hang", columnList = "ma_khach_hang"),
            @Index(name = "idx_ten_khach_hang", columnList = "ten_khach_hang"),
            @Index(name = "idx_gioi_tinh", columnList = "gioi_tinh")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KhachHang implements Serializable {
    @Id
    @Column(name="ma_khach_hang")
    private String maKhachHang;
    @Column(name = "ten_khach_hang")
    private String tenKhachHang;
    @Column(name = "so_dien_thoai", columnDefinition = "varchar(11)", unique = true, nullable = false)
    private String soDienThoai;
    @Column(name="gioi_tinh")
    @Enumerated(EnumType.STRING)
    protected GioiTinh gioiTinh;
    @Column(name="diem_tich_luy")
    private int diemTichLuy;


    @ToString.Exclude
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<HoaDon> hoaDons;

    public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, GioiTinh gioiTinh, int diemTichLuy) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.diemTichLuy = diemTichLuy;
    }
}
