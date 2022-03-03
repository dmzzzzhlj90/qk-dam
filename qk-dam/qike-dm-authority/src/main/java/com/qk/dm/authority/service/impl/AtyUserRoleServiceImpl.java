package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleBatchVO;
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
    public void addUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.addUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void deleteUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakApi.deleteUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getRoleName());
    }

    @Override
    public void addUserClientRole(AtyUserClientRoleBatchVO atyGroupBatchVO) {
        atyGroupBatchVO.getUserIds().forEach(userId -> keyCloakApi.addUserClientRole(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), userId, atyGroupBatchVO.getRoleName()));
    }
}
