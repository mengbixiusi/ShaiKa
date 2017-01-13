package com.xhhf.shaika.bean;

import java.io.Serializable;

/**
 * 返回数据解析的基类
 * Created by eric on 2016/11/9.
 */

public class NewResponse<T> implements Serializable{
//    private
    public boolean success;
    public int errorCode;
    public String msg;
    public T body;
}
