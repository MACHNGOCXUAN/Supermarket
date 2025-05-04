package dao;

import model.HoaDon;

import java.util.List;

public interface HoaDonDAO{
    boolean lapHoaDon(HoaDon hoaDon);

    List<HoaDon> findAll();

    HoaDon findById(String id);

    boolean delete(String id);

    boolean update(HoaDon hoaDon);

    Double getTongTien(String id);

}
