package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 11:07
 * @since 1.0.0
 */
@Service
public class AtyUserGroupServiceImpl implements AtyUserGroupService {
    private final KeyCloakApi keyCloakApi;

    public AtyUserGroupServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public List<AtyGroupInfoVO> getUserGroup(String userId, String realm) {
        return keyCloakApi.userGroup(realm, userId);
    }

    @Override
    public void addUserGroup(String userId, String groupId, String realm) {
        keyCloakApi.addUserGroup(realm, userId, groupId);
    }

    @Override
    public void addUserGroup( String groupId, AtyGroupBatchVO atyGroupBatchVO) {
        atyGroupBatchVO.getUserIds().forEach(userId -> keyCloakApi.addUserGroup(atyGroupBatchVO.getRealm(), userId, groupId));
    }

    @Override
    public void deleteUserGroup(String userId, String groupId, String realm) {
        keyCloakApi.deleteUserGroup(realm, userId, groupId);
    }
}
