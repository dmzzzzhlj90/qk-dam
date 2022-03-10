package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakGroupApi;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
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
    private final KeyCloakGroupApi keyCloakGroupApi;

    public AtyUserGroupServiceImpl(KeyCloakGroupApi keyCloakGroupApi) {
        this.keyCloakGroupApi = keyCloakGroupApi;
    }


    @Override
    public List<AtyGroupInfoVO> getUserGroup(String realm, String userId) {
        return keyCloakGroupApi.userGroup(realm, userId);
    }

    @Override
    public void addBatchByUsers(AtyUserGroupVO atyUserGroupVO) {
        keyCloakGroupApi.addUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void deleteUserGroup(AtyUserGroupVO atyUserGroupVO) {
        keyCloakGroupApi.deleteUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void addBatchByUsers(AtyGroupBatchByUsersVO atyGroupBatchByUsersVO) {
        atyGroupBatchByUsersVO.getUserIds().forEach(userId -> keyCloakGroupApi.addUserGroup(atyGroupBatchByUsersVO.getRealm(), userId, atyGroupBatchByUsersVO.getGroupId()));
    }

    @Override
    public void addBatchByGroups(AtyGroupBatchByGroupsVO batchByGroupsVO) {
        batchByGroupsVO.getGroupIds().forEach(groupId -> keyCloakGroupApi.addUserGroup(batchByGroupsVO.getRealm(), batchByGroupsVO.getUserId(), groupId));
    }
}
