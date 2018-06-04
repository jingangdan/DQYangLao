package com.dq.yanglao.base;

/**
 * Created by jingang on 2018/5/14.
 */

public class ListBean {
    private int img;
    private String title;

    public ListBean(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
