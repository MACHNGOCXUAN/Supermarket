package model;

import jakarta.persistence.Column;

import java.io.Serializable;

public enum GioiTinh implements Serializable {
    NAM("Nam"), NU("Nữ"), KHAC("Khác");

    private String gioiTinh;

    private GioiTinh(String gioiTinh){
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return  gioiTinh;
    }
}
