package com.xhhf.shaika.config;


public class Contants {
    public static final int CHARCOUNT = 15;

    //最多选择图片的数量  默认为1  最多选择9张
    public static final int SELECTLIMIT = 1;
    //请求的超时时间
    public static final int DEFAULT_MILLISECONDS = 10000;

    public static class API {
        //本地
        public static final String LOCALHOST = "http://10.0.2.2:8080/json.html";
        //服务器地址
        public static final String BASE_URL = "http://192.168.249.138/";
        //验证码登陆
        public static final String SLOGIN = BASE_URL + "app/slogin";
        //用户名密码登陆
        public static final String ULOGIN = BASE_URL + "app/ulogin";
        //注册
        public static final String REG = BASE_URL + "auth/reg";
        //获取公钥
        public static final String key = BASE_URL + "app/getPublicKey";
        //获取 验证码
        public static final String CODE = BASE_URL + "app/sendSms";
        //首页顶部信息
        public static final String BANNER = BASE_URL + "app/block/indexTop";
        //获取订单信息
        public static final String PAY = BASE_URL + "app/order/pay?orderId=b494f01c6d224256a9c5831c1fc2f18e";
        //上传图片
        public static final String UPIMG = BASE_URL + "app/file/upload";
        //修改用户信息
        public static final String MODIFY = BASE_URL + "app/user/modify";
        //商户列表
        public static final String BUSINESSLIST = BASE_URL + "app/merchant/list";
    }

    //    public static String getUrl(String src) {
////        return src;
//        return API.BASE_URL + src;
//    }
    public static String getUrl() {
        return API.LOCALHOST;
    }
}
