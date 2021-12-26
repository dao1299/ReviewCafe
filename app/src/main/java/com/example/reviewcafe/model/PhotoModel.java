package com.example.reviewcafe.model;

import java.io.Serializable;

public class PhotoModel implements Serializable {
    private String srcImg;

    public PhotoModel(String srcImg) {
        this.srcImg = srcImg;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }
}
