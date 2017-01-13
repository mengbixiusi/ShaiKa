package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * 用户状态
 * 作者：Eric on 2016/11/17 10:55
 * 邮箱：845505772@qq.com
 */
public class UserDict implements Serializable {
    private int isMerchant; //是否商户
    private int isMember;
    private int isSetPwd;//是否设置支付密码
    private int isSetPayPwd;//是否设置支付密码
    private int isSetEmail;//是否绑定邮箱
    private int isSetSafeQt;//是否设置安全问题
    private int isSetPhoto;//是否上传头像
    private int isSetName;//是否设置用户名
    private int isOpenLowGPRS;//是否开启低流量模式

    public int getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(int isMerchant) {
        this.isMerchant = isMerchant;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public int getIsSetPwd() {
        return isSetPwd;
    }

    public void setIsSetPwd(int isSetPwd) {
        this.isSetPwd = isSetPwd;
    }

    public int getIsSetPayPwd() {
        return isSetPayPwd;
    }

    public void setIsSetPayPwd(int isSetPayPwd) {
        this.isSetPayPwd = isSetPayPwd;
    }

    public int getIsSetEmail() {
        return isSetEmail;
    }

    public void setIsSetEmail(int isSetEmail) {
        this.isSetEmail = isSetEmail;
    }

    public int getIsSetSafeQt() {
        return isSetSafeQt;
    }

    public void setIsSetSafeQt(int isSetSafeQt) {
        this.isSetSafeQt = isSetSafeQt;
    }

    public int getIsSetPhoto() {
        return isSetPhoto;
    }

    public void setIsSetPhoto(int isSetPhoto) {
        this.isSetPhoto = isSetPhoto;
    }

    public int getIsSetName() {
        return isSetName;
    }

    public void setIsSetName(int isSetName) {
        this.isSetName = isSetName;
    }

    public int getIsOpenLowGPRS() {
        return isOpenLowGPRS;
    }

    public void setIsOpenLowGPRS(int isOpenLowGPRS) {
        this.isOpenLowGPRS = isOpenLowGPRS;
    }
}
