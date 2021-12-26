package com.example.reviewcafe.model;

public class CommentModel {
    private String imgUser;
    private String nameUser;
    private String contentComment;

    public CommentModel() {
    }

    public CommentModel(String imgUser, String nameUser, String contentComment) {
        this.imgUser = imgUser;
        this.nameUser = nameUser;
        this.contentComment = contentComment;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
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
