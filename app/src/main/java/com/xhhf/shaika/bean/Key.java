package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/9.
 */

public class Key implements Serializable {
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
