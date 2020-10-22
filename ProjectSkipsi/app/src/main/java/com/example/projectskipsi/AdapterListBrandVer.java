package com.example.projectskipsi;

public class AdapterListBrandVer {

    String nama_mobil,mesin,bahan_bakar,seat,transmisi,foto1,foto2,foto3,dimensi,detailmesin,eksterior,interior;

    public AdapterListBrandVer() {
    }

    public AdapterListBrandVer(String nama_mobil, String mesin, String bahan_bakar, String seat, String transmisi, String foto1, String foto2, String foto3, String dimensi, String detailmesin, String eksterior, String interior) {
        this.nama_mobil = nama_mobil;
        this.mesin = mesin;
        this.bahan_bakar = bahan_bakar;
        this.seat = seat;
        this.transmisi = transmisi;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.dimensi = dimensi;
        this.detailmesin = detailmesin;
        this.eksterior = eksterior;
        this.interior = interior;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getMesin() {
        return mesin;
    }

    public void setMesin(String mesin) {
        this.mesin = mesin;
    }

    public String getBahan_bakar() {
        return bahan_bakar;
    }

    public void setBahan_bakar(String bahan_bakar) {
        this.bahan_bakar = bahan_bakar;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTransmisi() {
        return transmisi;
    }

    public void setTransmisi(String transmisi) {
        this.transmisi = transmisi;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public String getDimensi() {
        return dimensi;
    }

    public void setDimensi(String dimensi) {
        this.dimensi = dimensi;
    }

    public String getDetailmesin() {
        return detailmesin;
    }

    public void setDetailmesin(String detailmesin) {
        this.detailmesin = detailmesin;
    }

    public String getEksterior() {
        return eksterior;
    }

    public void setEksterior(String eksterior) {
        this.eksterior = eksterior;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public void setKey(String key) {
    }
}
