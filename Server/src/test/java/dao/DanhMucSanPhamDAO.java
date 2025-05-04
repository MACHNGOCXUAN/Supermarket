package dao;

import model.DanhMucSanPham;

import java.util.List;

public interface DanhMucSanPhamDAO{
    boolean save(DanhMucSanPham danhMucSanPham);

    DanhMucSanPham findOne(String id);

    List<DanhMucSanPham> getList();

    boolean update(DanhMucSanPham danhMucSanPham);

    boolean delete(String id);
}
