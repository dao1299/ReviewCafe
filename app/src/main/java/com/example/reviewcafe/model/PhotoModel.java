package com.example.reviewcafe.model;

import java.io.Serializable;

public class PhotoModel implements Serializable {
    private int srcImg;

    public PhotoModel(int srcImg) {
        this.srcImg = srcImg;
    }

    public int getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(int srcImg) {
        this.srcImg = srcImg;
    }
}
