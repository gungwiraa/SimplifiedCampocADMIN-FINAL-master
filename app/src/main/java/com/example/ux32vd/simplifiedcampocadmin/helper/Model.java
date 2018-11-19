package com.example.ux32vd.simplifiedcampocadmin.helper;

public class Model {

    String id;
    String deskripsi;
    String foto;
    String lokasi;

    public Model(){

    }

    public Model(String id, String deskripsi, String foto, String lokasi) {
        this.id = id;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.lokasi = lokasi;
    }

    public String getId() {
        return id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public String getLokasi() {
        return lokasi;
    }
}
