package com.qk.dm.auth.jose;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import javax.crypto.SecretKey;

/**
 * JWT的密钥，JWT的密钥或者密钥对，统一称为JSON Web Key 也就是我们常说的 scret
 *
 * @author Joe Grandja
 */
public final class Jwks {

  private Jwks() {}

  public static RSAKey generateRsa() {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }

  public static ECKey generateEc() {
    KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
    ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
    ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
    Curve curve = Curve.forECParameterSpec(publicKey.getParams());
    return new ECKey.Builder(curve, publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }

  public static OctetSequenceKey generateSecret() {
    SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
    return new OctetSequenceKey.Builder(secretKey).keyID(UUID.randomUUID().toString()).build();
  }
}
