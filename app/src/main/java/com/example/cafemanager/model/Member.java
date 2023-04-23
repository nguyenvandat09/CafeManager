package com.example.cafemanager.model;

import java.io.Serializable;

public class Member implements Serializable {
    private int id;
    private String name;
    private String SDT;
    private int soPhanTramGiam;


    public Member(int id, String name, String SDT, int soPhanTramGiam) {
        this.id = id;
        this.name = name;
        this.SDT = SDT;
        this.soPhanTramGiam = soPhanTramGiam;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public float getSoPhanTramGiam() {
        return soPhanTramGiam;
    }

    public void setSoPhanTramGiam(int soPhanTramGiam) {
        this.soPhanTramGiam = soPhanTramGiam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
