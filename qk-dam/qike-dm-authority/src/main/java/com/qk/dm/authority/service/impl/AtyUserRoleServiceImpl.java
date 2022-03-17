package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakUserApi;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 16:48
 * @since 1.0.0
 */
@Service
public class AtyUserRoleServiceImpl implements AtyUserRoleService {
    private final KeyCloakUserApi keyCloakUserApi;

    public AtyUserRoleServiceImpl(KeyCloakUserApi keyCloakUserApi) {
        this.keyCloakUserApi = keyCloakUserApi;
    }

    @Override
    public List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId,String client_clientId) {
        return keyCloakUserApi.userClientRole(realm, userId, client_clientId);
    }

    @Override
    public void addBatchByUsers(AtyUserClientRoleVO userClientRole) {
        keyCloakUserApi.addUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getName());
    }

    @Override
    public void deleteUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakUserApi.deleteUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getName());
    }

    @Override
    public void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyGroupBatchVO.getUserIds().forEach(userId -> keyCloakUserApi.addUserClientRole(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), userId, atyGroupBatchVO.getName()));
    }

    @Override
    public void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO) {
        batchByRolesVO.getNames().forEach(roleName -> keyCloakUserApi.addUserClientRole(batchByRolesVO.getRealm(), batchByRolesVO.getClient_id(), batchByRolesVO.getUserId(), roleName));
    }
}
