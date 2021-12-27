package com.example.reviewcafe.model;

import java.util.List;

public class UserModel {
    private String Uid;
    private String email;
    private String name;
    private String urlImg;
    private List<String> listFavorite;

    public UserModel(String uid, String email, String name, String urlImg, List<String> listFavorite) {
        Uid = uid;
        this.email = email;
        this.name = name;
        this.urlImg = urlImg;
        this.listFavorite = listFavorite;
    }

    public UserModel() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public List<String> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<String> listFavorite) {
        this.listFavorite = listFavorite;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "Uid='" + Uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", urlImg='" + urlImg + '\'' +
                ", listFavorite=" + listFavorite +
                '}';
    }
}
