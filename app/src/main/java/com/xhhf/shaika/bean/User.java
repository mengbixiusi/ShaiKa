package com.xhhf.shaika.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户信息
 * 作者：Eric on 2016/11/17 11:00
 * 邮箱：845505772@qq.com
 */

public class User implements Serializable {
    @SerializedName("data")
    private AppUser appUser;
    private String token;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
