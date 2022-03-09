package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 16:48
 * @since 1.0.0
 */
@Service
public class AtyUserRoleServiceImpl implements AtyUserRoleService {
    private final KeyCloakApi keyCloakApi;

    @Value("${keycloak.role.client_id}")
    private String client_id;

    @Value("${keycloak.role.client_clientId}")
    private String client_clientId;

    public AtyUserRoleServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId) {
        return keyCloakApi.userClientRole(realm, userId, client_clientId);
    }

    @Override
    public void addBatchByUsers(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.addUserClientRole(userClientRole.getRealm(), client_id, userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void deleteUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.deleteUserClientRole(userClientRole.getRealm(), client_id, userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyGroupBatchVO.getUserIds().forEach(userId -> keyCloakApi.addUserClientRole(atyGroupBatchVO.getRealm(), client_id, userId, atyGroupBatchVO.getRoleName()));
    }

    @Override
    public void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO) {
        batchByRolesVO.getRoleNames().forEach(roleName -> keyCloakApi.addUserClientRole(batchByRolesVO.getRealm(), client_id, batchByRolesVO.getUserId(), roleName));
    }
}
