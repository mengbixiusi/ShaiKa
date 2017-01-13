package com.xhhf.shaika.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.xhhf.shaika.R;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by eric on 16-8-11.
 */
public class ClassPathResource {

    /**
     * 验证手机号错误提示信息
     *
     * @param context
     * @param mobile 手机号
     * @return
     */
    public static String errorHintMobile(Context context, String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return context.getString(R.string.error_mobile1_toast);
        }
        if (isMobileNO(mobile)) {
            return "";
        } else {
            return context.getString(R.string.error_mobile2_toast);
        }
    }


    /**
     * 判断手机号是否符合格式
     *
     * @param mobile 手机号码
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        String str = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 身份证号正则
     *
     * @param code
     * @return
     */
    public static boolean isCardId(String code) {
        if (!TextUtils.isEmpty(code)) {
            if (code.length() != 18) {
                return false;
            }
        } else {
            return false;
        }
        String isIDCard2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(isIDCard2);
        Matcher m = p.matcher(code);
        return m.matches();
    }

    public static boolean ispwd(String pwd) {
        String isIDCard2 = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$";
        Pattern p = Pattern.compile(isIDCard2);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


    /**
     * 正则表达式工具类
     * bucong by jaingbin on 16-8-11.
     */

    /**
     * 判断两个字符串是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEquals(String a, String b) {
        return a == b || a != null && a.equals(b);
    }

    // 判断手机号的正则表达式；
    public static boolean initdata(EditText ed1) {
        // TODO Auto-generated method stub
        String regExp = "^1(3[0-9]|4[57]|5[0-35-9]|8[025-9])\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(ed1.getText().toString());
        return m.matches();
    }

    /***
     * 判断是不是手机号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid2(String phoneNumber) {
        Pattern p = Pattern
                .compile("^1(3[0-9]|4[57]|5[0-35-9]|8[025-9])\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();

    }


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串、null 返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmptyOrNull(String input) {
        if (input == null || "".equals(input)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将字符编码转换成UTF-8
     */
    public static String toUTF_8(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, "UTF-8");
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换的字符串
     * @param newCharset 目标编码
     */
    public static String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            return new String(bs, newCharset); // 用新的字符编码生成字符串
        }
        return null;
    }

    /**
     * 编码URL中的汉字（UTF-8）
     *
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString().replace(" ", "%20");
    }

    /***
     * 防止重复提交
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
