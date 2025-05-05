package com.example.myapplication;

public class Not {
    private int id;
    private String baslik;
    private String icerik;

    public Not(int id, String baslik, String icerik) {
        this.id = id;
        this.baslik = baslik;
        this.icerik = icerik;
    }

    public int getId() {
        return id;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getIcerik() {
        return icerik;
    }
}


