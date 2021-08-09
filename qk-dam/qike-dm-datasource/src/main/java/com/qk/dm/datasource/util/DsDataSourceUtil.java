package com.qk.dm.datasource.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 数据源连接用户名密码加密解密操作
 *
 * @author zys
 * @date 2021/8/3 11:47
 * @since 1.0.0
 */
public class DsDataSourceUtil {
  private static final Logger logger = LoggerFactory.getLogger(DsDataSourceUtil.class);
  public static final String KEY_ALGORITHM = "RSA";

  // 公钥
  private static final String s =
      "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIrk2uBmybT/mcJ5DGTrOWpjvyKfcRaSQt9Si8YE+FpbqgZpiy/NApCOcXgJO44/CNlDDRLgdtqPiZ5+USnfdrECAwEAAQ==";
  public static final byte[] publicKey = Base64.decodeBase64(s);
  // 私钥
  private static final String s1 =
      "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAiuTa4GbJtP+ZwnkMZOs5amO/Ip9xFpJC31KLxgT4WluqBmmLL80CkI5xeAk7jj8I2UMNEuB22o+Jnn5RKd92sQIDAQABAkACdETBzk8mGZYX75eeeOGM1bJc5EBVS8ROueCqYRAaWJmc8E/uU9rZ8NdP5SHhncgNVb+ND6mu4Atzs+m13I2hAiEAyUdv+WT7W5ndgViFh/TlY07fBU/Oh1zKiy0HYOm+lf0CIQCwp47SNBiTjfi650Oj9WPjosCvJ5wpSvu/RdU+jzanxQIgOzEX++a3iMup+WTX+BCL+PVz3rMGkIY4Rk+asORsmN0CIEl5R4PSOy6T7BIAwjddhvBU7wNq3B6om1CQwQLGGhtpAiA/OKK45/Jdr+WmjdtzeSeRg8bcpLzbHx2X/26FAvm4EQ==";
  public static final byte[] privateKey = Base64.decodeBase64(s1);

  /**
   * 私钥加密
   *
   * @param data 待加密数据
   * @return byte[] 加密数据
   */
  public static String encryptByPrivateKey(byte[] data) throws Exception {

    // 取得私钥
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    // 生成私钥
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
    // 数据加密
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    return Base64.encodeBase64String(cipher.doFinal(data));
  }

  /**
   * 公钥解密
   *
   * @param data 待解密数据
   * @return byte[] 解密数据
   */
  public static String decryptByPublicKey(byte[] data) throws Exception {

    // 实例化密钥工厂
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    // 初始化公钥
    // 密钥材料转换
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
    // 产生公钥
    PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
    // 数据解密
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, pubKey);
    return new String(cipher.doFinal(data));
  }
}
