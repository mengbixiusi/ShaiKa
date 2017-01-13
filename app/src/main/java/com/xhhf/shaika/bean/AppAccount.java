package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * 作者：Eric on 2016/11/17 10:52
 * 邮箱：845505772@qq.com
 */
public class AppAccount implements Serializable {
    private double money;
    private int score;
    private int delFlag;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
