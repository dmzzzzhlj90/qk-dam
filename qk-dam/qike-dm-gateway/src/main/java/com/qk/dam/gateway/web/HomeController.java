package com.qk.dam.gateway.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Controller
public class HomeController {
    @GetMapping("/")
    public Mono<Void> index(ServerHttpResponse response,
                            Authentication authentication,
                            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                            @AuthenticationPrincipal OAuth2User oauth2User){
        String azp = oauth2User.getAttribute("azp");
        if (Objects.requireNonNull(azp).equals("qk-dam-front")){
            return Mono.fromRunnable(()->{
                response.setStatusCode(HttpStatus.FOUND);
                HttpHeaders headers = response.getHeaders();
                headers.setLocation(URI.create("http://localhost:8000/user/login?t="+authorizedClient.getAccessToken().getTokenValue()));

            });
        }
        return Mono.fromRunnable(()-> response.setStatusCode(HttpStatus.FOUND));


    }

}
