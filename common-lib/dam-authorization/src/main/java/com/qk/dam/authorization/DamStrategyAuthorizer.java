package com.qk.dam.authorization;

import cn.hutool.core.io.resource.ResourceUtil;
import com.qk.dam.commons.enums.SysType;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public final class DamStrategyAuthorizer implements DamAuthorizer {
    public DamStrategyAuthorizer() {
        throw new UnsupportedOperationException();
    }

    private static final DamAuthzPolicy DAM_AUTH_POLICY;

    static {
        log.info("====> 默认权限策略初始化");
        DAM_AUTH_POLICY = new Yaml().loadAs(
                ResourceUtil.getStream("auth-v1.0.0/authz-policy.yml"),
                DamAuthzPolicy.class);
        log.info("====> 初始化完成");
    }

    /**
     * 获取所有角色
     *
     * @return List<DamAuthzPolicy.AuthzRole> 角色信息
     */
    public static List<DamAuthzPolicy.AuthzRole> getRoles() {
        // 角色
        Map<String, DamAuthzPolicy.AuthzRole> roles = DAM_AUTH_POLICY.getRoles();
        roles.forEach((key, v) -> v.setRoleName(key));
        return new ArrayList<>(roles.values());
    }

    public static List<DamAuthzPolicy.AuthzRole> getRoles(Collection<String> roleArray) {
        // 角色
        Map<String, DamAuthzPolicy.AuthzRole> roles = DAM_AUTH_POLICY.getRoles();
        return roles.entrySet().stream()
                .filter(entry -> roleArray.contains(entry.getKey()))
                .map(entry -> {
                    entry.getValue().setRoleName(entry.getKey());
                    return entry.getValue();
                }).collect(Collectors.toList());
    }

    /**
     * 所有角色组的角色信息
     *
     * @return List<DamAuthzPolicy.AuthzRole> 角色信息
     */
    public static List<DamAuthzPolicy.AuthzRole> getGroupRoles() {
        Map<String, DamAuthzPolicy.AuthzRole> roles = DAM_AUTH_POLICY.getRoles();
        roles.forEach((key, v) -> v.setRoleName(key));
        // 角色组
        Map<String, List<String>> groupRoles = DAM_AUTH_POLICY.getGroupRoles();
        return groupRoles.keySet()
                .stream()
                .map(roles::get)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色组获取角色
     *
     * @param roleGroup 角色组
     * @return List<DamAuthzPolicy.AuthzRole> 角色信息
     */
    public static List<DamAuthzPolicy.AuthzRole> getGroupRoles(String roleGroup) {
        Map<String, DamAuthzPolicy.AuthzRole> roles = DAM_AUTH_POLICY.getRoles();
        roles.forEach((key, v) -> v.setRoleName(key));
        Map<String, List<String>> groupRoles = DAM_AUTH_POLICY.getGroupRoles();
        List<String> roleKeys = groupRoles.get(roleGroup);
        return roleKeys.stream()
                .map(roles::get)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccessAllowed() {
        return false;
    }
}
