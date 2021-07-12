package com.qk.dm.auth.config;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

public class InnerRegisteredClient {
        private String id;
        private String clientId;
        private String clientSecret;
        private String clientName;
        private Set<String> redirectUris;
        private Set<String> scopes;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }


        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }


        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }


        public Set<String> getRedirectUris() {
            return redirectUris;
        }

        public void setRedirectUris(Set<String> redirectUris) {
            this.redirectUris = redirectUris;
        }

        public Set<String> getScopes() {
            return scopes;
        }

        public void setScopes(Set<String> scopes) {
            this.scopes = scopes;
        }

    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public void setAuthorizationGrantTypes(Set<AuthorizationGrantType> authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(Set<ClientAuthenticationMethod> clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }
}