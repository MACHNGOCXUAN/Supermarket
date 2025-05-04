package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(
        name = "danhmucsanphams",
        indexes = {
                @Index(name = "idx_ma_danh_muc_san_pham", columnList = "ma_danh_muc_san_pham")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@NoArgsConstructor
public class DanhMucSanPham implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ma_danh_muc_san_pham", columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String maDanhMucSanPham;
    @Column(name = "ten_danh_muc_san_pham", columnDefinition = "varchar(150)", nullable = false)
    private String tenDanhMucSanPham;

    @ToString.Exclude
    @OneToMany(mappedBy = "danhMucSanPham", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SanPham> sanPhams;

    public DanhMucSanPham(String maDanhMucSanPham, String tenDanhMucSanPham, List<SanPham> sanPhams) {
        this.maDanhMucSanPham = maDanhMucSanPham;
        this.tenDanhMucSanPham = tenDanhMucSanPham;
        this.sanPhams = sanPhams;
    }
    public  DanhMucSanPham(String maDanhMucSanPham, String tenDanhMucSanPham) {
        this.maDanhMucSanPham = maDanhMucSanPham;
        this.tenDanhMucSanPham = tenDanhMucSanPham;
    }
}
