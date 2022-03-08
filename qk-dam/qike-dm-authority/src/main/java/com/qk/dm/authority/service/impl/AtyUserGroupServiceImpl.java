package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
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
    public void addBatchByUsers(AtyUserGroupVO atyUserGroupVO) {
        keyCloakApi.addUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void deleteUserGroup(AtyUserGroupVO atyUserGroupVO) {
        keyCloakApi.deleteUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void addBatchByUsers(AtyGroupBatchByUsersVO atyGroupBatchByUsersVO) {
        atyGroupBatchByUsersVO.getUserIds().forEach(userId -> keyCloakApi.addUserGroup(atyGroupBatchByUsersVO.getRealm(), userId, atyGroupBatchByUsersVO.getGroupId()));
    }

    @Override
    public void addBatchByGroups(AtyGroupBatchByGroupsVO batchByGroupsVO) {
        batchByGroupsVO.getGroupIds().forEach(groupId -> keyCloakApi.addUserGroup(batchByGroupsVO.getRealm(), batchByGroupsVO.getUserId(), groupId));
    }

    @Override
    public List<AtyUserInfoVO> getUserGroupUsers(String realm, String groupId) {
        return keyCloakApi.getUserGroupUsers(realm,groupId);
    }

}
