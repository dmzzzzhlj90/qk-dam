package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
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
    private final KeyCloakApi keyCloakApi;

    public AtyUserRoleServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId, String client_clientId) {
        return keyCloakApi.userClientRole(realm, userId, client_clientId);
    }

    @Override
    public void addBatchByUsers(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.addUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void deleteUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.deleteUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyGroupBatchVO.getUserIds().forEach(userId -> keyCloakApi.addUserClientRole(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), userId, atyGroupBatchVO.getRoleName()));
    }

    @Override
    public void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO) {
        batchByRolesVO.getRoleNames().forEach(roleName -> keyCloakApi.addUserClientRole(batchByRolesVO.getRealm(), batchByRolesVO.getClient_id(), batchByRolesVO.getUserId(), roleName));
    }


}
