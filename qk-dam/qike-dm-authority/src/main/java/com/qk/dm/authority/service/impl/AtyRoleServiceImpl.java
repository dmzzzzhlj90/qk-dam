package com.qk.dm.authority.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
@Service
public class AtyRoleServiceImpl implements AtyRoleService {
    private final KeyCloakApi keyCloakApi;

    public AtyRoleServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public void addClientRole(String realm, String client_id, String roleName, String description) {
        keyCloakApi.addClientRole(realm, client_id, roleName, description);
    }

    @Override
    public void updateClientRole(String realm, String client_id, String roleName, String description) {
        keyCloakApi.updateClientRole(realm, client_id, roleName, description);
    }

    @Override
    public void deleteClientRole(String realm, String client_id, String roleName) {
        keyCloakApi.deleteClientRole(realm, client_id, roleName);
    }

    @Override
    public AtyClientRoleInfoVO getClientRole(String realm, String client_id, String roleName) {
        return keyCloakApi.clientRoleDetail(realm, client_id, roleName);
    }

    @Override
    public PageResultVO<AtyClientRoleInfoVO> getClientRoles(String realm,String client_id, String search, Pagination pagination) {
        return keyCloakApi.clientRoleList(realm, client_id, search, pagination);
    }

    @Override
    public List<AtyClientRoleInfoVO> getUsers(String realm, String client_id, String search) {
        return keyCloakApi.clientRoleList(realm, client_id, search);
    }
}
