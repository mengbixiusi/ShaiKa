package com.xhhf.shaika.safe;

import android.util.Base64;

import com.xhhf.shaika.util.Share;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Eric
 * @ClassName: com.qust.SecretUtils
 * @Description: 3DES加密解密工具类
 * @date 2016-11-4
 */
public class DES3Utils {

    // 定义加密算法，DESede即3DES
    private static final String Algorithm = "DESede";
    //    private static final String Algorithm = "desede/CBC/PKCS5Padding";
    // 加密密钥
    private static final String PASSWORD_CRYPT_KEY = "shaika";

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static String encryptMode(String src, String key) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);
            // 实例化Cipher
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            String s = new String(Base64.encode(cipher.doFinal(src.getBytes()), Base64.DEFAULT));
            return s;
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 根据字符串生成密钥24位的字节数组
     *
     * @param keyStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr)
            throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
//        Share.i();
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
}