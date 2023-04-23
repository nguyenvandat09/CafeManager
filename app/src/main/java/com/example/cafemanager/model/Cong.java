package com.example.cafemanager.model;

import java.io.Serializable;

public class Cong  implements Serializable{
    private int id;
    private int idnv;
    private String ngaynghi;
    private String LyDo;

    public Cong(int id, int idnv, String ngaynghi, String lyDo) {
        this.id = id;
        this.idnv = idnv;
        this.ngaynghi = ngaynghi;
        this.LyDo = lyDo;
    }



    public int getIdnv() {
        return idnv;
    }

    public void setIdnv(int idnv) {
        this.idnv = idnv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgaynghi() {
        return ngaynghi;
    }

    public void setNgaynghi(String ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String lyDo) {
        LyDo = lyDo;
    }
}
