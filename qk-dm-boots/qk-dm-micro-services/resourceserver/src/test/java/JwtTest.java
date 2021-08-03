import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.qk.dam.resourceserver.jwt.JoseHeader;
import com.qk.dam.resourceserver.jwt.JwtClaimsSet;
import com.qk.dam.resourceserver.jwt.JwtEncoder;
import com.qk.dam.resourceserver.jwt.NimbusJwsEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.tsugi.lti13.LTI13Util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JwtTest {
    public static void main(String[] args) {
        RSAPrivateKey privateKey = getPrivateKey(JwtTest.privateKey);
        RSAPublicKey publicKey = getPublicKey(JwtTest.publicKey);
        JWKSource<SecurityContext> jwkSource = jwkSource("qk-dam-front", privateKey, publicKey);

        JwtEncoder jwtEncoder = new NimbusJwsEncoder(jwkSource);
        JwtDecoder jwtDecoder = jwtDecoder(jwkSource);

        JoseHeader.Builder joseHeaderBuilder = JoseHeader.withAlgorithm(SignatureAlgorithm.RS256);
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        claimsBuilder
                .subject("主体-加密测试")
                .claim("datasource","mysql")
                .claim("url","mysql")
                .claim("driver","mysql")
                .audience(Collections.singletonList("qike"))
                .issuedAt(Instant.now());

        Jwt encode = jwtEncoder.encode(joseHeaderBuilder.build(), claimsBuilder.build());
        System.out.println(encode.getTokenValue());

        Jwt decode = jwtDecoder.decode(encode.getTokenValue());

        System.out.println(decode.getClaims());

    }
    public static JWKSource<SecurityContext> jwkSource(String clientId,
                                                RSAPrivateKey privateKey,
                                                RSAPublicKey publicKey) {
        JWKSet jwkSet = new JWKSet(new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(clientId)
                .build());

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    public static JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        Set<JWSAlgorithm> jwsAlgs = new HashSet<>();
        jwsAlgs.addAll(JWSAlgorithm.Family.RSA);
        jwsAlgs.addAll(JWSAlgorithm.Family.EC);
        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> jwsKeySelector =
                new JWSVerificationKeySelector<>(jwsAlgs, jwkSource);
        jwtProcessor.setJWSKeySelector(jwsKeySelector);
        // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it instead
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
        });
        return new NimbusJwtDecoder(jwtProcessor);
    }
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
    final static String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5D1SylkcQT+FCD\n" +
            "qud9CIaI7+n5wrJRNnZxpdRc3L/RUuQFldhhKWhLZcljubk94HecEkvdrNJTODhMw\n" +
            "DYKzOyriSlYYW1I4Uh8tk7nNSNlinT5DaBOSrn3BRZsTtDUHFrE2TlMFFybDBb7Mx\n" +
            "8Xbhn22sS//uQ3QSpo0lJaAgPVDRR1LLl/UWCN6KQOfMp1rY0rw4bP676UQ2mn43j\n" +
            "tazesZ2988ICjYXGURRnz0JIIzjTOPrDz3w9MkkTCOVVtHTyEyBkPHZw5GfFdYSgG\n" +
            "7K/6nbAdPUypd4xBr4uumro6zOT6CwyJJ81ri4hOhuNfVMTnRr0qU7NrVKyDANdH1\n" +
            "slAgMBAAECggEAHrsYAowIAwJw0qpi/1jiLNsQ4Y3xcZjYYFylFiaIyzEzqhq7qfs\n" +
            "AEjLi7Z68Yvti+pBE5vfcL+jvUxZIJp2N+TcH6Nq3/GuImSmdv1N8zIJKMeCs2Gqo\n" +
            "4FXgn0BaKgR6wSA4WgLbE5g5s5owHtrlW4HJVx9hJB4/vvfZnLIE3J6Dw4uxzNafp\n" +
            "uVCsdOFQkCMBycbyeeBylPhcF/ETgkgMGsQtznCdyBmyJ+SPbe9kzeT/ci03Tos2p\n" +
            "i+WupoFO5LOC3/kBPzpNZth2EbZPptF7rb9rfIOhd/Mw1MfRc/UZtzLofbpkcDLjr\n" +
            "tXEvZ9FHfY6pHuCSjVlvbSKEwOyj1YQKBgQDaIMkr4Fr2v1MUVcq21MwOKZ8v9M58\n" +
            "rFd8UoPczxLXwVx7gWKTF2vFjK5zhONCvINp4LNvbdF5FJKuGclythZ73Y2dIDGD5\n" +
            "7Z4F1GB6bfOvTlC6uCtbxPRUKZ7tmuk+3ECRcBuUGgWZAQdWURuoXfPkQ+wxZOjis\n" +
            "eI3FYbsTp6rQKBgQDZMMCBMwX2d7Eq6ZTGuRDQFhdNac4MdciNKLQatARsfs46Auy\n" +
            "95LyauqBrpbEvHBk0jtCpSDr7WrycOnWA5NwX7ly65U3IVzcN9XJ9B5h2deibLjIw\n" +
            "5xK2vwI/04MmKdGt6ByieXGaaI+0uwpkrdS758F8SV/3c8AK40WuLFkpWQKBgCVY2\n" +
            "bjmeAypY3sm2UeGlc2S+MuIOdaE15HUuvkSh2JjnAcVdyBCMVSoqrHivdqOVl3JeE\n" +
            "+K2QPHLvoaNjCq1pysqXOOsIuqH8beDzmUH8GJCoXXm1J2o0kCaw7hg/rChm3iLXl\n" +
            "10OyMgVvZDet3QAFNEAevPIES4Okg+KlTJfUdAoGAAbM4/onotQowz9YuPPP75rUV\n" +
            "j5yRaVWOsVQtPLxGAJdpg9i8A6yDsW5ejRkp7uV4aQnpJjoThB4stchbXRvtgR5zi\n" +
            "V78IeJU4v4V3Fav4dWAcZ6F9A4K/nU2TkuYjjbrlkHn33m14vCIl3CTX0BhFut0f0\n" +
            "WPw78bNLqctAVS2VkCgYBYEN+yR5Gc95dkzhLUSiA3vV4OHfrwNizhUoc5ThrsCAc\n" +
            "jxxrLs009T2FKodff3huxquodxms/vmz0+eJyEHo2HhURieDMKaRTJUeFXKD6UK8E\n" +
            "UAjGBK93PPkb/s3Z1UyIbdi5J/JkbZZSoNYkEezAwao5K+MFVun5LY2LnI8OnQ==\n" +
            "-----END PRIVATE KEY-----\n";
    final static String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuQ9UspZHEE/hQg6rnfQiG\n" +
            "iO/p+cKyUTZ2caXUXNy/0VLkBZXYYSloS2XJY7m5PeB3nBJL3azSUzg4TMA2Cszsq\n" +
            "4kpWGFtSOFIfLZO5zUjZYp0+Q2gTkq59wUWbE7Q1BxaxNk5TBRcmwwW+zMfF24Z9t\n" +
            "rEv/7kN0EqaNJSWgID1Q0UdSy5f1FgjeikDnzKda2NK8OGz+u+lENpp+N47Ws3rGd\n" +
            "vfPCAo2FxlEUZ89CSCM40zj6w898PTJJEwjlVbR08hMgZDx2cORnxXWEoBuyv+p2w\n" +
            "HT1MqXeMQa+Lrpq6Oszk+gsMiSfNa4uITobjX1TE50a9KlOza1SsgwDXR9bJQIDAQ\n" +
            "AB\n" +
            "-----END PUBLIC KEY-----\n";
}
