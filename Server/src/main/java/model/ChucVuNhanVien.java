package model;

import java.io.Serializable;

public enum ChucVuNhanVien implements Serializable {
    NGUOIQUANLY("Người quản lý"), NHANVIEN("Nhân viên");

    private String chucVu;

    private ChucVuNhanVien(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public String toString() {
        return chucVu ;
    }
}
