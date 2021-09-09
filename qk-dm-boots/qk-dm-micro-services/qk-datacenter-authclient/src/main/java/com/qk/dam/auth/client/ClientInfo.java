package com.qk.dam.auth.client;

public class ClientInfo {
    private String frontend;
    private String loginSuccessUrl;

    public ClientInfo() {
    }

    public ClientInfo(String frontend,String loginSuccessUrl) {
        this.frontend = frontend;
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getFrontend() {
        return frontend;
    }

    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }
}
