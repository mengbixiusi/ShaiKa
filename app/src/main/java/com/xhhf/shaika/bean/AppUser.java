package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * 作者：Eric on 2016/11/17 10:46
 * 邮箱：845505772@qq.com
 */

public class AppUser implements Serializable {
    private String userName;
    private String phone;
    private String email;
    private String sex;
    private String photo;
    private String sign;
    private String qrcode;
    private String loginDate;
    private int score;
    private AppAccount appAccount;
    private UserDict userDict;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    public UserDict getUserDict() {
        return userDict;
    }

    public void setUserDict(UserDict userDict) {
        this.userDict = userDict;
    }
}
