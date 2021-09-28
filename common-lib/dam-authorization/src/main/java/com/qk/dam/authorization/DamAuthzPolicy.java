package com.qk.dam.authorization;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DamAuthzPolicy {
    private String version;
    private String name;
    private String namespace;
    private String desc;
    private Map<String,AuthzRole> roles;
    private Map<String, List<String>>   userRoles;
    private Map<String, List<String>>   groupRoles;

    @Data
    public static class AuthzRole {
        private String roleName;
        private List<String> action;
        private List<String> mvcMatchers;
        private String desc;
    }
}
