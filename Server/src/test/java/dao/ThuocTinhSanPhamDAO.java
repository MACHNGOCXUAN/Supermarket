package dao;

import model.ThuocTinhSanPham;

import java.util.List;

public interface ThuocTinhSanPhamDAO{
    List<ThuocTinhSanPham> getListByProductId(String id);

    boolean update(ThuocTinhSanPham thuocTinhSanPham);

    boolean save(ThuocTinhSanPham thuocTinhSanPham);

    boolean delete(String id);
}
