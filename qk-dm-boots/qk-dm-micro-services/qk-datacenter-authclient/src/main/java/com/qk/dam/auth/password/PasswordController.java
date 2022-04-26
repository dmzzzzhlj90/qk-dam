package com.qk.dam.auth.password;

import com.google.gson.JsonObject;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.commons.util.GsonUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shenpj
 * @date 2022/4/12 15:51
 * @since 1.0.0
 */
@RestController
@RefreshScope
public class PasswordController {
    final RestTemplate restTemplate = new RestTemplate();
    private String openid_connect = KeyCloakUrlProperty.issuer_uri + "/protocol/openid-connect";
    private String logout = openid_connect + "/logout";
    private String token = openid_connect + "/token";
    private String introspect = token + "/introspect";
    private String parameter = "client_secret=" + KeyCloakUrlProperty.clientSecret + "&client_id=" + KeyCloakUrlProperty.clientId;


    /**
     * 密码登陆
     *
     * @param loginInfoVO
     * @return DefaultCommonResult<TokenInfoVO>
     */
    @RequestMapping(value = "/auth/password/login", method = RequestMethod.POST)
    public DefaultCommonResult<TokenInfoVO> password_login(@RequestBody LoginInfoVO loginInfoVO) {
        try {
            return DefaultCommonResult.success(ResultCodeEnum.OK, login(loginInfoVO));
        } catch (BizException n) {
            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, n.getMessage());
        }
    }

    //todo 底下几个都需要更改请求方法及参数封装

    /**
     * 验证token
     *
     * @param request
     */
    @RequestMapping(value = "/auth/token/introspect", method = RequestMethod.POST)
    public DefaultCommonResult<Boolean> token_introspect(HttpServletRequest request) {
        try {
            return DefaultCommonResult.success(ResultCodeEnum.OK, introspect(request.getHeader("access_token")));
        } catch (Exception n) {
            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, n.getMessage());
        }
    }

    /**
     * 刷新token
     *
     * @param refresh_token
     */
    @RequestMapping(value = "/auth/token/refresh", method = RequestMethod.GET)
    public void token_refresh(String refresh_token) {
        refresh(refresh_token);
    }

    /**
     * 退出
     *
     * @param refresh_token
     */
    @RequestMapping(value = "/auth/token/logout", method = RequestMethod.GET)
    public void token_logout(String refresh_token) {
        logout(refresh_token);
    }


    public TokenInfoVO login(LoginInfoVO loginInfoVO) {
        String data = parameter + "&grant_type=password" +
                "&username=" + loginInfoVO.getUsername() +
                "&password=" + loginInfoVO.getPassword();
        AccessTokenResponse accessToken = keycloakPost(token, data, AccessTokenResponse.class);
        return TokenInfoVO
                .builder()
                .access_token(accessToken.getToken())
                .refresh_token(accessToken.getRefreshToken())
                .expires_in(accessToken.getExpiresIn())
                .build();
    }

    public Boolean introspect(String token) {
        String data = parameter + "&token=" + token;
        String introspectResult = keycloakPost(introspect, data, String.class);
        JsonObject jsonObject = GsonUtil.toJsonObject(introspectResult);
        Boolean active = jsonObject.get("active").getAsBoolean();
//        if(active){
//            System.out.println("有效");
//        }else{
//            System.out.println("无效");
//        }
        return active;
    }

    public TokenInfoVO refresh(String refresh_token) {
        String data = parameter + "grant_type=refresh_token&refresh_token=" + refresh_token;
        AccessTokenResponse accessToken = keycloakPost(token, data, AccessTokenResponse.class);
        return TokenInfoVO
                .builder()
                .access_token(accessToken.getToken())
                .refresh_token(accessToken.getRefreshToken())
                .expires_in(accessToken.getExpiresIn())
                .build();
    }

    public void logout(String refresh_token) {
        String data = parameter + "&refresh_token=" + refresh_token;
        keycloakPost(logout, data, String.class);
    }


    private <T> T keycloakPost(String uri, String data, Class<T> clazz) {
        ResponseEntity<T> response;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<String> entity = new HttpEntity<>(data, headers);
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, clazz);
//            System.out.println("retult:" + response.getBody());
            if (response.getStatusCode().value() != HttpStatus.OK.value()
                    && response.getStatusCode().value() != HttpStatus.NO_CONTENT.value()) {
                throw new BizException("账号或密码错误");
            }
        } catch (HttpClientErrorException e) {
            throw new BizException("账号或密码错误");
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        } catch (Exception e) {
            throw new BizException("内部错误，请联系管理员");
        }
        return response.getBody();
    }


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
