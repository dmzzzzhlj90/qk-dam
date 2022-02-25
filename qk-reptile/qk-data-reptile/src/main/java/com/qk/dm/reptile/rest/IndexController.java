package com.qk.dm.reptile.rest;

import com.qk.dm.reptile.constant.RptUserConstant;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

/**
 * 退出接口和登陆成功后的首页
 */
@Controller
public class IndexController {

    /**
     * 登录成功后的首页
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        HttpServletResponse response){
        SecurityContext securityContext =
                (SecurityContext) request.getSession().getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        OidcUser principal = (DefaultOidcUser) securityContext.getAuthentication().getPrincipal();
        String userName = principal.getUserInfo().getPreferredUsername();
        Cookie cookie = new Cookie(RptUserConstant.USER_SESSION_KEY,userName);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
        return  "index.html";
    }

    /**
     * 退出
     * @param request
     * @param response
     * @param authorizedClient
     * @return
     */
    @RequestMapping(path = "/current/logout")
    public String currentLogout(
            HttpServletRequest request,
            HttpServletResponse response,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        Arrays.stream(request.getCookies()).forEach(e->{
            e.setMaxAge(0);
            e.setPath("/");
            response.addCookie(e);
        });
        SecurityContext securityContext =
                (SecurityContext) request.getSession().getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        OidcUser principal = (DefaultOidcUser) securityContext.getAuthentication().getPrincipal();
        OidcIdToken idToken = principal.getIdToken();
        String clientName = authorizedClient.getClientRegistration().getClientName();

        WebClient webClient = WebClient.create(clientName + "/protocol/openid-connect/logout");

                  webClient
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder.queryParam("id_token_hint", idToken.getTokenValue()).build())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();

        request.getSession().removeAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        request.getSession().removeAttribute(RptUserConstant.USER_SESSION_KEY);
        //前端存储的session，退出后清除
        request.getSession().removeAttribute(RptUserConstant.BUTTON_POWER);
        return  "redirect:/";
    }


}
