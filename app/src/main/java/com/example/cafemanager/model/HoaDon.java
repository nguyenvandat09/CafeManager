package com.example.cafemanager.model;

import java.io.Serializable;
import java.util.Date;

public class HoaDon implements Serializable {
    private int maHoaDon;
    private int manv;
    private int makvip;
    private String ngayMua;
    private int soban;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int manv, int makvip, String ngayMua, int soban) {
        this.maHoaDon = maHoaDon;
        this.manv = manv;
        this.makvip = makvip;
        this.ngayMua = ngayMua;
        this.soban = soban;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMakvip() {
        return makvip;
    }

    public void setMakvip(int makvip) {
        this.makvip = makvip;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getSoban() {
        return soban;
    }

    public void setSoban(int soban) {
        this.soban = soban;
    }
}
