package com.qk.dam.auth.password;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author shenpj
 * @date 2022/4/12 15:51
 * @since 1.0.0
 */
@RestController
@RefreshScope
public class PasswordController {
    final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.security.oauth2.client.provider.qkdatacenter.issuer-uri}")
    private String issuer_uri;
    @Value("${spring.security.oauth2.client.registration.qkdatacenter.client-id}")
    private String clientId;

    /**
     * 密码登陆
     *
     * @param loginInfoVO
     * @return DefaultCommonResult<TokenInfoVO>
     */
    @RequestMapping(value = "/auth/password/login", method = RequestMethod.POST)
    public DefaultCommonResult<TokenInfoVO> password_login(@RequestBody LoginInfoVO loginInfoVO) {
        try {
            TokenInfoVO tokenInfoVO = TokenInfoVO
                    .builder()
                    .access_token(login(loginInfoVO).getToken())
                    .build();
            return DefaultCommonResult.success(ResultCodeEnum.OK, tokenInfoVO);
        } catch (BizException n) {
            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, n.getMessage());
        }
    }

    @RequestMapping(value = "/auth/password/login2", method = RequestMethod.GET)
    public void password_login2(String token)  {
        introspect(token);
    }

    @RequestMapping(value = "/auth/password/login3", method = RequestMethod.GET)
    public void password_login3(String refresh_token) {
        refresh(null, refresh_token);
    }

    @RequestMapping(value = "/auth/password/login4", method = RequestMethod.GET)
    public void password_login4(String refresh_token) {
        logout(null, refresh_token);
    }

    public AccessTokenResponse login(LoginInfoVO loginInfoVO) {
        String uri = issuer_uri + "/protocol/openid-connect/token";
        String data = "grant_type=password" +
                "&username=" + loginInfoVO.getUsername() +
                "&password=" + loginInfoVO.getPassword() +
                "&client_id=" + clientId;
        return keycloakPost(uri, data, AccessTokenResponse.class);
    }

    public void introspect(String token) {
        String uri = issuer_uri + "/protocol/openid-connect/token/introspect";
        String data = "client_secret=" + "6fdfce20-1f50-43a2-bbbe-62337e35c3d9" +
                "&token=" + token +
                "&client_id=" + clientId;
        keycloakPost(uri, data, String.class);
    }

    public void refresh(String client_secret, String refresh_token) {
        String uri = issuer_uri + "/protocol/openid-connect/token";
        String data = "grant_type=refresh_token" +
                "&client_secret=" + "6fdfce20-1f50-43a2-bbbe-62337e35c3d9" +
                "&refresh_token=" + refresh_token +
                "&client_id=" + clientId;
        keycloakPost(uri, data, String.class);
    }

    public void logout(String client_secret, String refresh_token) {
        String uri = issuer_uri + "/protocol/openid-connect/logout";
        String data = "client_secret=" + "6fdfce20-1f50-43a2-bbbe-62337e35c3d9" +
                "&refresh_token=" + refresh_token +
                "&client_id=" + clientId;
        keycloakPost(uri, data, String.class);
    }


    private <T> T keycloakPost(String uri, String data, Class<T> clazz) {
        ResponseEntity<T> response;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<String> entity = new HttpEntity<>(data, headers);
            response = restTemplate.exchange(uri,
                    HttpMethod.POST, entity, clazz);
            System.out.println("retult:" + response.getBody().toString());
        }catch (Exception e){
            throw new BizException("内部错误，请联系管理员");
        }
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new BizException("账号或密码错误");
        }
        return response.getBody();
    }


//    http://10.80.27.69:8180/auth/realms/quickstart/protocol/openid-connect/token/introspect
//    client_id=app-springboot-confidential&client_secret=3acf7692-49cb-4c45-9943-6f3dba512dae

//    http://10.80.27.69:8180/auth/realms/quickstart/protocol/openid-connect/token
//    client_id=app-springboot-confidential&client_secret=3acf7692-49cb-4c45-9943-6f3dba512dae&grant_type=refresh_token&refresh_token=asdfasd


//    @RequestMapping(value = "/auth/password/login2", method = RequestMethod.GET)
//    public DefaultCommonResult password_login2(LoginInfoVO loginInfoVO, HttpServletResponse response) throws IOException {
//        try {
//            AccessTokenResponse login = loginTest(loginInfoVO);
//            System.out.println(login.getToken());
//            Cookie cookie = new Cookie("token", login.getToken());
//            cookie.setMaxAge(60 * 60);
//            response.addCookie(cookie);
//            response.sendRedirect(loginInfoVO.getUrl());
//            return DefaultCommonResult.success();
//        } catch (BizException n) {
//            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, "账号或密码错误");
//        } catch (Exception e) {
//            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, "内部错误，请联系管理员");
//        }
//    }
}
