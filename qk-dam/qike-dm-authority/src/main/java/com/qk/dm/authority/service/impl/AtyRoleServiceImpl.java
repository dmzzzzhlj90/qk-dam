package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakRoleApi;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.service.AtyRoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
@Service
public class AtyRoleServiceImpl implements AtyRoleService {
    private final KeyCloakRoleApi keyCloakRoleApi;

    @Value("${keycloak.role.client_id}")
    private String client_id;

    public AtyRoleServiceImpl(KeyCloakRoleApi keyCloakRoleApi) {
        this.keyCloakRoleApi = keyCloakRoleApi;
    }

    @Override
    public void addClientRole(String realm, String roleName, String description) {
        keyCloakRoleApi.addClientRole(realm, client_id, roleName, description);
    }

    @Override
    public void updateClientRole(String realm, String roleName, String description) {
        keyCloakRoleApi.updateClientRole(realm, client_id, roleName, description);
    }

    @Override
    public void deleteClientRole(String realm, String roleName) {
        keyCloakRoleApi.deleteClientRole(realm, client_id, roleName);
    }

    @Override
    public AtyClientRoleInfoVO getClientRole(String realm, String roleName) {
        return keyCloakRoleApi.clientRoleDetail(realm, client_id, roleName);
    }

    @Override
    public PageResultVO<AtyClientRoleInfoVO> getClientRoles(String realm, String search, Pagination pagination) {
        return keyCloakRoleApi.clientRoleList(realm, client_id, search, pagination);
    }

    @Override
    public List<AtyClientRoleInfoVO> getUsers(String realm, String search) {
        return keyCloakRoleApi.clientRoleList(realm, client_id, search);
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getRoleUsers(String realm, String roleName, Pagination pagination) {
        return keyCloakRoleApi.clientRoleUsers(realm, client_id, roleName,pagination);
    }

    @Override
    public List<AtyUserInfoVO> getRoleUsers(String realm, String roleName) {
        return keyCloakRoleApi.clientRoleUsers(realm, client_id, roleName);
    }
}
