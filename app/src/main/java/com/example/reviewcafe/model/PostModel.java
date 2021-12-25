package com.example.reviewcafe.model;

public class PostModel {
    private String idPost;
    private String titlePost;
    private long srcImg;
    private String authorPost;
    private String pricePost;
    private String addressPost;

    public PostModel(String idPost, String titlePost, long srcImg, String authorPost, String pricePost, String addressPost) {
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

    public long getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(long srcImg) {
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
                '}';
    }
}
