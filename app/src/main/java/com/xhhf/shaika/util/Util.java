package com.xhhf.shaika.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.safe.DES3Utils;
import com.xhhf.shaika.safe.RSAUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 工具类
 * Created by Eric on 2016/11/9.
 */

public class Util {
    private static final Gson gson;
    private static String charValue;

    static {
        gson = new Gson();
    }

    /**
     * 产生随机字符串
     *
     * @param charCount
     * @return
     */
    private static String getRandStr(int charCount) {
        charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 26) + 'A');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    /**
     * 随机数
     *
     * @param from
     * @param to
     * @return
     */
    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    /**
     * 转换为json
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    /**
     * 参数转换
     *
     * @param map
     * @return
     */
    public static String paramsEncoding(Map<String, String> map) {
        String randStr = getRandStr(Contants.CHARCOUNT).toUpperCase();
        Share.i("randStr" + randStr);
        map.put("des3key", randStr);
        return params(toJson(map));
    }

    /**
     * 处理过的参数字符串
     *
     * @param value
     * @return
     */
    private static String params(String value) {
        HashMap map = new HashMap();
        Share.i("大写 " + charValue);
        String key = RSAUtils.encryptByPublic(charValue);
        value = DES3Utils.encryptMode(value, charValue);
        String token = ShaiKaApplication.sp.getString("token", "");
        if (!TextUtils.isEmpty(token))
            map.put("token", token);
        map.put("key", key);
        map.put("value", value);
        return toJson(map);
    }
}
