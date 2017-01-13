package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * 轮播图实体类
 * Created by eric on 2016/11/11.
 */

public class Banner implements Serializable{
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
