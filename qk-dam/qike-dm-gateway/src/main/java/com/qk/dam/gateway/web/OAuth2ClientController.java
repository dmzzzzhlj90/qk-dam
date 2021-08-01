package com.qk.dam.gateway.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OAuth2ClientController {

  @GetMapping("/oauth2/accessToken")
  @ResponseBody
  public String accessToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
    return authorizedClient.getAccessToken().getTokenValue();
  }

  @GetMapping("/oauth2/idToken")
  @ResponseBody
  public DefaultOidcUser idToken(
      Authentication authentication, @AuthenticationPrincipal OAuth2User oauth2User) {
    return (DefaultOidcUser) authentication.getPrincipal();
  }
}
