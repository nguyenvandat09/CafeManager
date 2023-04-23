package com.example.cafemanager.model;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String taiKhoan;
    private int maThuThu;
    private String maKhau;
    private String hoTenThuThu;
    private float luong;
    private String SDT;
   // private byte[] thuthuPhoto;
    public NhanVien() {
    }


    public NhanVien(int maThuThu, String maKhau) {
        this.maThuThu = maThuThu;
        this.maKhau = maKhau;
    }

    public NhanVien(String taiKhoan, int maThuThu, String maKhau, String hoTenThuThu, float luong, String SDT) {
        this.taiKhoan = taiKhoan;
        this.maThuThu = maThuThu;
        this.maKhau = maKhau;
        this.hoTenThuThu = hoTenThuThu;
        this.luong = luong;
        this.SDT = SDT;
    }

    public float getLuong() {
        return luong;
    }

    public void setLuong(float luong) {
        this.luong = luong;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public int getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(int maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getMaKhau() {
        return maKhau;
    }

    public void setMaKhau(String maKhau) {
        this.maKhau = maKhau;
    }

    public String getHoTenThuThu() {
        return hoTenThuThu;
    }

    public void setHoTenThuThu(String hoTenThuThu) {
        this.hoTenThuThu = hoTenThuThu;
    }
}
