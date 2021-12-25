package com.example.reviewcafe.model;

import android.net.Uri;

import java.util.List;

public class PostModel {
    private String idPost;
    private String titlePost;
    private Uri srcImg;
    private String authorPost;
    private String pricePost;
    private String addressPost;
    private String time;
    private String description;
    private List<Uri> listImg;
    private String district;
    private String theLoai;

    public PostModel(String idPost, String titlePost, Uri srcImg, String authorPost, String pricePost, String addressPost, String time, String description, List<Uri> listImg, String district, String theLoai) {
        this.idPost = idPost;
        this.titlePost = titlePost;
        this.srcImg = srcImg;
        this.authorPost = authorPost;
        this.pricePost = pricePost;
        this.addressPost = addressPost;
        this.time = time;
        this.description = description;
        this.listImg = listImg;
        this.district = district;
        this.theLoai = theLoai;
    }

    public PostModel(String idPost, String titlePost, Uri srcImg, String authorPost, String pricePost, String addressPost, String time, String description, List<Uri> listImg, String district) {
        this.idPost = idPost;
        this.titlePost = titlePost;
        this.srcImg = srcImg;
        this.authorPost = authorPost;
        this.pricePost = pricePost;
        this.addressPost = addressPost;
        this.time = time;
        this.description = description;
        this.listImg = listImg;
        this.district = district;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Uri> getListImg() {
        return listImg;
    }

    public void setListImg(List<Uri> listImg) {
        this.listImg = listImg;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public PostModel(String idPost, String titlePost, Uri srcImg, String authorPost, String pricePost, String addressPost) {
        this.idPost = idPost;
        this.titlePost = titlePost;
        this.srcImg = srcImg;
        this.authorPost = authorPost;
        this.pricePost = pricePost;
        this.addressPost = addressPost;
    }

    public PostModel() {
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public Uri getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(Uri srcImg) {
        this.srcImg = srcImg;
    }

    public String getAuthorPost() {
        return authorPost;
    }

    public void setAuthorPost(String authorPost) {
        this.authorPost = authorPost;
    }

    public String getPricePost() {
        return pricePost;
    }

    public void setPricePost(String pricePost) {
        this.pricePost = pricePost;
    }

    public String getAddressPost() {
        return addressPost;
    }

    public void setAddressPost(String addressPost) {
        this.addressPost = addressPost;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "idPost='" + idPost + '\'' +
                ", titlePost='" + titlePost + '\'' +
                ", srcImg=" + srcImg +
                ", authorPost='" + authorPost + '\'' +
                ", pricePost='" + pricePost + '\'' +
                ", addressPost='" + addressPost + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", listImg=" + listImg +
                ", district='" + district + '\'' +
                ", theLoai='" + theLoai + '\'' +
                '}';
    }
}
