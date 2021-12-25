package com.example.reviewcafe.model;

public class CommentModel {
    private int imgUser;
    private String nameUser;
    private String contentComment;

    public CommentModel() {
    }

    public CommentModel(int imgUser, String nameUser, String contentComment) {
        this.imgUser = imgUser;
        this.nameUser = nameUser;
        this.contentComment = contentComment;
    }

    public int getImgUser() {
        return imgUser;
    }

    public void setImgUser(int imgUser) {
        this.imgUser = imgUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }
}
