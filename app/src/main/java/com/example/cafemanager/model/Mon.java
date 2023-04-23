package com.example.cafemanager.model;

public class Mon {
    private int id;
    private String tenSach;
    private String tenLoaiSach;
    private float money;
    private byte[] tPhoto;
    private int idLoaiSach;

    public Mon() {
    }

    public Mon(int id, String tenSach, String tenLoaiSach, float money, byte[] tPhoto, int idLoaiSach) {
        this.id = id;
        this.tenSach = tenSach;
        this.tenLoaiSach = tenLoaiSach;
        this.money = money;
        this.tPhoto = tPhoto;
        this.idLoaiSach = idLoaiSach;
    }

    public byte[] gettPhoto() {
        return tPhoto;
    }

    public void settPhoto(byte[] tPhoto) {
        this.tPhoto = tPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }




    public int getIdLoaiSach() {
        return idLoaiSach;
    }

    public void setIdLoaiSach(int idLoaiSach) {
        this.idLoaiSach = idLoaiSach;
    }
}
