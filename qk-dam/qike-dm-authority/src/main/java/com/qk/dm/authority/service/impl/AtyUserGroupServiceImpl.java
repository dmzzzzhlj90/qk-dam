package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
        //????????????id?????????????????????????????????
        List<String> userIdList =dealAddGroupUsers(batchByUsersVO);
        userIdList.forEach(userId -> keyCloakUserApi.addUserGroup(batchByUsersVO.getRealm(), userId, batchByUsersVO.getGroupId()));
    }

    /**
     * ????????????id??????????????????????????????
     * @param batchByUsersVO
     * @return
     */
    private List<String> dealAddGroupUsers(AtyGroupBatchByUsersVO batchByUsersVO) {
        //?????????????????????-????????????
        List<AtyUserInfoVO> atyUserInfoVOList = keyCloakGroupApi.groupUsers(batchByUsersVO.getRealm(), batchByUsersVO.getGroupId());
        //???????????????????????????id
        List<String> unboundIdList = CollectionUtils.isEmpty(atyUserInfoVOList) ? new ArrayList<>() : queryUnboundIdList(atyUserInfoVOList,batchByUsersVO);
        //???????????????????????????id
        List<String> bindingList = CollectionUtils.isEmpty(batchByUsersVO.getUserIds()) ? new ArrayList<>() : queryBindingIdList(batchByUsersVO);
        if (CollectionUtils.isNotEmpty(unboundIdList)){
            //????????????
            unboundIdList.forEach(userId->{
                keyCloakUserApi.deleteUserGroup(batchByUsersVO.getRealm(), userId, batchByUsersVO.getGroupId());
            });
        }
        return bindingList;
    }

    /**
     * ???????????????????????????id??????
     * @param batchByUsersVO
     * @return
     */
    private List<String> queryBindingIdList(AtyGroupBatchByUsersVO batchByUsersVO) {
        //?????????????????????-????????????
        List<AtyUserInfoVO> atyUserInfoVOList = keyCloakGroupApi.groupUsers(batchByUsersVO.getRealm(), batchByUsersVO.getGroupId());
        //?????????????????????????????????
        if (CollectionUtils.isEmpty(atyUserInfoVOList)){
            return batchByUsersVO.getUserIds();
        }else{
            List<String> idList = atyUserInfoVOList.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
            Iterator<String> iterator = batchByUsersVO.getUserIds().iterator();
            while (iterator.hasNext()){
                String id = iterator.next();
                if (idList.contains(id)){
                    iterator.remove();
                }
            }
            return  batchByUsersVO.getUserIds();
        }
    }

    /**
     * ???????????????????????????id??????
     * @param atyUserInfoVOList
     * @param batchByUsersVO
     * @return
     */
    private List<String> queryUnboundIdList(List<AtyUserInfoVO> atyUserInfoVOList, AtyGroupBatchByUsersVO batchByUsersVO) {
        if (CollectionUtils.isEmpty(batchByUsersVO.getUserIds())){
            return atyUserInfoVOList.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
        }else{
            Iterator<AtyUserInfoVO> iterator = atyUserInfoVOList.iterator();
            while (iterator.hasNext()){
                AtyUserInfoVO atyUserInfoVO = iterator.next();
                if (batchByUsersVO.getUserIds().contains(atyUserInfoVO.getId())){
                    iterator.remove();
                }
            }
            return atyUserInfoVOList.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
        }
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
        //??????????????????
        List<AtyUserInfoVO> userInfoVOS = keyCloakUserApi.userList(userFiltroVO.getRealm(), userFiltroVO.getSearch());
        //?????????????????????
        List<AtyUserInfoVO> userInfoVOS1 = keyCloakGroupApi.groupUsers(userFiltroVO.getRealm(), userFiltroVO.getGroupId());
        userInfoVOS.removeAll(userInfoVOS1);
        return userInfoVOS;
    }

    @Override
    public List<AtyGroupInfoVO> getGroupFiltro(AtyUserGroupFiltroVO groupFiltroVO) {
        //?????????????????????
        List<AtyGroupInfoVO> groups = keyCloakGroupApi.groupList(groupFiltroVO.getRealm(), groupFiltroVO.getSearch());
        //??????????????????????????????
        List<AtyGroupInfoVO> userGroups = keyCloakUserApi.userGroup(groupFiltroVO.getRealm(), groupFiltroVO.getUserId());
        groups.removeAll(userGroups);
        return groups;
    }
}
