package com.example.cafemanager.model;

public class Kindof {
    private int idLoaiSach;
    private String tenloaiSach;

    public Kindof() {
    }

    public Kindof(int idLoaiSach, String tenloaiSach) {
        this.idLoaiSach = idLoaiSach;
        this.tenloaiSach = tenloaiSach;
    }

    public int getIdLoaiSach() {
        return idLoaiSach;
    }

    public void setIdLoaiSach(int idLoaiSach) {
        this.idLoaiSach = idLoaiSach;
    }

    public String getTenloaiSach() {
        return tenloaiSach;
    }

    public void setTenloaiSach(String tenloaiSach) {
        this.tenloaiSach = tenloaiSach;
    }
}
