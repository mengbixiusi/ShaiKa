package com.xhhf.shaika.safe;

//import android.util.Base64;
//
//import java.io.ByteArrayOutputStream;
//import java.security.Key;
//import java.security.KeyFactory;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.X509EncodedKeySpec;
//
//import javax.crypto.Cipher;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by eric on 2016/11/3.
 */
public class RSAUtils {

        private static final String RSA_PUBLICE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoYO2+DnAPY2AS92qkUuS7udI0Kzc0Vpslr5kuwB8AKfwxzPUtqfxl5vT0Z2kryRpZTPCGoNnYVv5tMnxzMY/FTjQ9/hOruk518JLg//ESbz6hyj1fzTtT5WhPvsNIM6q7cKLcPiju80l0+WNSAKDdQ1YSEh4tYFvWjfsASWeEKubGZJJGfu5+1amtwRK8fve71DgbRXm4KJz9f7Yoy6rbcVFie0a5beTwcl2whdN2hI1i9R+P95S+rZhD/xY2+gV56kUOuMG21ECQWDZzbHnA/h/PzIBmJWPmkdDnCJ4UILArKGXZcb2HiiD9bcrI9G+3SVKAbcnBVgOF6x77cZQtQIDAQAB";
    private static final String ALGORITHM = "RSA";

    /**
     * 得到公钥
     *
     * @param algorithm
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String algorithm,
                                                  String bysKey) throws NoSuchAlgorithmException, Exception {
        byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }

    /**
     * 使用公钥加密
     *
     * @param content
     * @param
     * @return
     */
    public static String encryptByPublic(String content) {
        try {
//            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, ShaiKaApplication.sp.getString("", ""));
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            String s = new String(Base64.encode(output, Base64.DEFAULT));
            return s;

        } catch (Exception e) {
            return null;
        }
    }

}
