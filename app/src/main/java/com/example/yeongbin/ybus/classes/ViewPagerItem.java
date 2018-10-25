package com.example.yeongbin.ybus.classes;

import android.widget.ImageView;

public class ViewPagerItem {

    private ImageView imageView;
    private String picnum;

    public ViewPagerItem(ImageView imageView, String picnum) {
        this.imageView = imageView;
        this.picnum =  picnum;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getPicnum() {
        return picnum;
    }

    public void setPicnum(String picnum) {
        this.picnum = picnum;
    }
}
