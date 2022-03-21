package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakGroupApi;
import com.qk.dam.authority.common.keycloak.KeyCloakUserApi;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.*;
import com.qk.dm.authority.vo.user.AtyUserGroupFiltroVO;
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
    private final KeyCloakGroupApi keyCloakGroupApi;

    public AtyUserGroupServiceImpl(KeyCloakUserApi keyCloakUserApi, KeyCloakGroupApi keyCloakGroupApi) {
        this.keyCloakUserApi = keyCloakUserApi;
        this.keyCloakGroupApi = keyCloakGroupApi;
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

    @Override
    public PageResultVO<AtyUserInfoVO> getGroupUsers(AtyGroupUserParamVO groupUserParamVO, String groupId) {
        return keyCloakGroupApi.groupUsers(groupUserParamVO.getRealm(),groupId,groupUserParamVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getGroupUsers(String realm, String groupId) {
        return keyCloakGroupApi.groupUsers(realm,groupId);
    }

    @Override
    public List<AtyUserInfoVO> getUserFiltro(AtyGroupUserFiltroVO userFiltroVO) {
        //查询所有用户
        List<AtyUserInfoVO> userInfoVOS = keyCloakUserApi.userList(userFiltroVO.getRealm(), userFiltroVO.getSearch());
        //查询已授权用户
        List<AtyUserInfoVO> userInfoVOS1 = keyCloakGroupApi.groupUsers(userFiltroVO.getRealm(), userFiltroVO.getGroupId());
        userInfoVOS.removeAll(userInfoVOS1);
        return userInfoVOS;
    }

    @Override
    public List<AtyGroupInfoVO> getGroupFiltro(AtyUserGroupFiltroVO groupFiltroVO) {
        //查询所有用户组
        List<AtyGroupInfoVO> groups = keyCloakGroupApi.groupList(groupFiltroVO.getRealm(), groupFiltroVO.getSearch());
        //查询用户已授权用户组
        List<AtyGroupInfoVO> userGroups = keyCloakUserApi.userGroup(groupFiltroVO.getRealm(), groupFiltroVO.getUserId());
        groups.removeAll(userGroups);
        return groups;
    }
}
