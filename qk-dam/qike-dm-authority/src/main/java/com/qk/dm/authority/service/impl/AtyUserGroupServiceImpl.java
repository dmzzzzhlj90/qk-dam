package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyUserGroupBatchVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
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
    public List<AtyGroupInfoVO> getUserGroup(String realm, String userId) {
        return keyCloakApi.userGroup(realm, userId);
    }

    @Override
    public void addUserGroup(AtyUserGroupVO atyUserGroupVO) {
        keyCloakApi.addUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void deleteUserGroup(AtyUserGroupVO atyUserGroupVO) {
        keyCloakApi.deleteUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void addUserGroup( AtyUserGroupBatchVO atyUserGroupBatchVO) {
        atyUserGroupBatchVO.getUserIds().forEach(userId -> keyCloakApi.addUserGroup(atyUserGroupBatchVO.getRealm(), userId, atyUserGroupBatchVO.getGroupId()));
    }
}
