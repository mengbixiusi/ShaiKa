package com.xhhf.shaika.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class HomeUpper implements Serializable{

    /**
     * success : true
     * errorCode : 0
     * msg : 操作成功！
     * body : {"imgList":[{"id":"1","url":null,"name":"图片1","src":"/asd/asda1.jpg","sign":"真是个好图片1","order":"1"},{"id":"2","url":null,"name":"图片2","src":"/asd/asd2a.jpg","sign":"真是个好图片2","order":"2"}],"typeList":[{"id":"1","icon":"/asd/asda1.jpg","name":"美食","isHot":0,"isNew":0,"order":"1"},{"id":"2","icon":"/asd/asda2.jpg","name":"电影","isHot":0,"isNew":0,"order":"2"}],"merchantList":[{"id":"1","icon":"/asd/asda1.jpg","name":"好想你","isHot":0,"isNew":0,"order":"1"},{"id":"2","icon":"/asd/asda2.jpg","name":"图咖啡","isHot":0,"isNew":0,"order":"2"}]}
     */

    public boolean success;
    public String errorCode;
    public String msg;
    public BodyBean body;


    public static class BodyBean implements Serializable {
        /**
         * id : 1
         * url : null
         * name : 图片1
         * src : /asd/asda1.jpg
         * sign : 真是个好图片1
         * order : 1
         */

        public List<ImgListBean> imgList;
        /**
         * id : 1
         * icon : /asd/asda1.jpg
         * name : 美食
         * isHot : 0
         * isNew : 0
         * order : 1
         */

        public List<TypeListBean> typeList;
        /**
         * id : 1
         * icon : /asd/asda1.jpg
         * name : 好想你
         * isHot : 0
         * isNew : 0
         * order : 1
         */

        public List<MerchantListBean> merchantList;

        public static class ImgListBean implements Serializable {
            public String id;
            public Object url;
            public String name;
            public String src;
            public String sign;
            public String order;
        }

        public static class TypeListBean implements Serializable {
            public String id;
            public String icon;
            public String name;
            public int isHot;
            public int isNew;
            public String order;
        }

        public static class MerchantListBean implements Serializable {
            public String id;
            public String icon;
            public String name;
            public int isHot;
            public int isNew;
            public String order;
        }
    }
}
