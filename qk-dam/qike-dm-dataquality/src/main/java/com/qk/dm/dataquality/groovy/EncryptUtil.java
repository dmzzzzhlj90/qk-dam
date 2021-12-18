package com.qk.dm.dataquality.groovy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.experimental.UtilityClass;

/**
 * encrypt 加密工具类
 *
 * @author wjq
 * @date 2021/12/15
 * @since 1.0.0
 */
@UtilityClass
public class EncryptUtil {

    public static final String SHA_256 = "SHA-256";
    public static final String SHA_512 = "SHA-512";
    public static final String MD_5 = "MD5";

    /**
     * 传入文本内容，返回 encrypt-256 串
     */
    public String SHA256(final String strText) {
        return encrypt(strText, SHA_256);
    }

    /**
     * 传入文本内容，返回 encrypt-512 串
     */
    public String SHA512(final String strText) {
        return encrypt(strText, SHA_512);
    }

    public String MD5(final String strText){
        return encrypt(strText, MD_5);
    }

    /**
     * 字符串 encrypt 加密
     */
    private String encrypt(final String str, final String strType) {
        MessageDigest messageDigest;
        String encodeStr = "";
        if (null == str || str.length() == 0) {
            return encodeStr;
        }
        try {
            messageDigest = MessageDigest.getInstance(strType);
            messageDigest.update(str.getBytes());

            // 将byte 转换为字符展示出来
            StringBuilder stringBuffer = new StringBuilder();
            String temp;
            for (byte aByte : messageDigest.digest()) {
                temp = Integer.toHexString(aByte & 0xFF);
                if (temp.length() == 1) {
                    //1得到一位的进行补0操作
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            encodeStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}