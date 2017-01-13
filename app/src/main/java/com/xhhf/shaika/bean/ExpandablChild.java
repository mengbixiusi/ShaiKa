package com.xhhf.shaika.bean;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class ExpandablChild {

    private String categray;
    private String discount;

    public ExpandablChild() {
        categray = "乔丹共享了500元";
        discount = "8折";
    }

    public String getCategray() {
        return categray;
    }

    public void setCategray(String categray) {
        this.categray = categray;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
