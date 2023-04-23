package com.example.cafemanager.model;

import java.io.Serializable;

public class HDCT implements Serializable {
    private int maHDCT;
    private int hoaDon;
    private String namesp;
    private int soLuongMua;
    private float gia;

    public HDCT(int maHDCT, int hoaDon, String namesp, int soLuongMua, float gia) {
        this.maHDCT = maHDCT;
        this.hoaDon = hoaDon;
        this.namesp = namesp;
        this.soLuongMua = soLuongMua;
        this.gia = gia;
    }

    public String getNamesp() {
        return namesp;
    }

    public void setNamesp(String namesp) {
        this.namesp = namesp;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(int hoaDon) {
        this.hoaDon = hoaDon;
    }


    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public HDCT() {
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
}
