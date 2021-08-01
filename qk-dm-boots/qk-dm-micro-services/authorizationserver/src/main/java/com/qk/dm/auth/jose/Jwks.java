package com.qk.dm.auth.jose;

import com.nimbusds.jose.jwk.*;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.SecretKey;
import org.tsugi.lti13.LTI13Util;

/**
 * JWT的密钥，JWT的密钥或者密钥对，统一称为JSON Web Key 也就是我们常说的 scret
 *
 * @author Joe Grandja
 */
public final class Jwks {

  private Jwks() {}

  public static String getPublicEncoded(RSAKey rsaKey) throws Exception {
    return LTI13Util.getPublicEncoded(rsaKey.toKeyPair());
  }

  public static RSAPublicKey getPublicKey(String publicKey) {
    return (RSAPublicKey) LTI13Util.string2PublicKey(publicKey);
  }

  public static String getPrivateEncoded(RSAKey rsaKey) throws Exception {
    return LTI13Util.getPrivateEncoded(rsaKey.toKeyPair());
  }

  public static RSAPrivateKey getPrivateKey(String privateKey) {
    return (RSAPrivateKey) LTI13Util.string2PrivateKey(privateKey);
  }

  public static RSAKey generateRsa(String keyID) {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(keyID).build();
  }

  public static ECKey generateEc(String keyID) {
    KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
    ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
    ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
    Curve curve = Curve.forECParameterSpec(publicKey.getParams());
    return new ECKey.Builder(curve, publicKey).privateKey(privateKey).keyID(keyID).build();
  }

  public static OctetSequenceKey generateSecret(String keyID) {
    SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
    return new OctetSequenceKey.Builder(secretKey).keyID(keyID).build();
  }
}
