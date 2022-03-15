package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakUserApi;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import com.qk.dm.authority.vo.user.AtyUserGroupParamVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 11:07
 * @since 1.0.0
 */
@Service
public class AtyUserGroupServiceImpl implements AtyUserGroupService {
    private final KeyCloakUserApi keyCloakUserApi;

    public AtyUserGroupServiceImpl(KeyCloakUserApi keyCloakUserApi) {
        this.keyCloakUserApi = keyCloakUserApi;
    }

    @Override
    public PageResultVO<AtyGroupInfoVO> getUserGroup(AtyUserGroupParamVO userGroupParamVO, String userId) {
        return keyCloakUserApi.userGroup(userGroupParamVO.getRealm(), userId, userGroupParamVO.getPagination());
    }

    @Override
    public List<AtyGroupInfoVO> getUserGroup(String realm, String userId) {
        return keyCloakUserApi.userGroup(realm, userId);
    }

    @Override
    public void addBatchByUsers(AtyUserGroupVO atyUserGroupVO) {
        keyCloakUserApi.addUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void deleteUserGroup(AtyUserGroupVO atyUserGroupVO) {
        keyCloakUserApi.deleteUserGroup(atyUserGroupVO.getRealm(), atyUserGroupVO.getUserId(), atyUserGroupVO.getGroupId());
    }

    @Override
    public void addBatchByUsers(AtyGroupBatchByUsersVO batchByUsersVO) {
        batchByUsersVO.getUserIds().forEach(userId -> keyCloakUserApi.addUserGroup(batchByUsersVO.getRealm(), userId, batchByUsersVO.getGroupId()));
    }

    @Override
    public void addBatchByGroups(AtyGroupBatchByGroupsVO batchByGroupsVO) {
        batchByGroupsVO.getGroupIds().forEach(groupId -> keyCloakUserApi.addUserGroup(batchByGroupsVO.getRealm(), batchByGroupsVO.getUserId(), groupId));
    }
}
