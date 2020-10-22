package com.example.projectskipsi;

public class AdapterListReview {

    String namamobil,review,photo_mobil,photo_user,username;

    public AdapterListReview() {
    }

    public AdapterListReview(String namamobil, String review, String photo_mobil, String photo_user, String username) {
        this.namamobil = namamobil;
        this.review = review;
        this.photo_mobil = photo_mobil;
        this.photo_user = photo_user;
        this.username = username;
    }

    public String getNamamobil() {
        return namamobil;
    }

    public void setNamamobil(String namamobil) {
        this.namamobil = namamobil;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPhoto_mobil() {
        return photo_mobil;
    }

    public void setPhoto_mobil(String photo_mobil) {
        this.photo_mobil = photo_mobil;
    }

    public String getPhoto_user() {
        return photo_user;
    }

    public void setPhoto_user(String photo_user) {
        this.photo_user = photo_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(String key) {
    }
}
