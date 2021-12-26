package com.example.reviewcafe.model;



import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class PostModel implements Serializable {
    private String idAuthor;
    private String titlePost;
    private String srcImg;
    private String authorPost;
    private String pricePost;
    private String addressPost;
    private String time;
    private String description;
    private List<String> listImg;
    private String district;
    private String theLoai;


    public PostModel(String idPost, String titlePost, String srcImg, String authorPost, String pricePost, String addressPost, String time, String description, List<String> listImg, String district, String theLoai) {
        this.idAuthor = idPost;
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

    public PostModel(String idPost, String titlePost, String srcImg, String authorPost, String pricePost, String addressPost, String time, String description, List<String> listImg, String district) {
        this.idAuthor = idPost;
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

    public List<String> getListImg() {
        return listImg;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public void setListImg(List<String> listImg) {
        this.listImg = listImg;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public PostModel(String idPost, String titlePost, String srcImg, String authorPost, String pricePost, String addressPost) {
        this.idAuthor = idPost;
        this.titlePost = titlePost;
        this.srcImg = srcImg;
        this.authorPost = authorPost;
        this.pricePost = pricePost;
        this.addressPost = addressPost;
    }

    public PostModel() {
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
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
                "idPost='" + idAuthor + '\'' +
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
