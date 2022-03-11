package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakGroupApi;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupUserParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 17:44
 * @since 1.0.0
 */
@Service
public class AtyGroupServiceImpl implements AtyGroupService {

    private final KeyCloakGroupApi keyCloakGroupApi;

    public AtyGroupServiceImpl(KeyCloakGroupApi keyCloakGroupApi) {
        this.keyCloakGroupApi = keyCloakGroupApi;
    }

    @Override
    public void addGroup(AtyGroupVO groupVO) {
        keyCloakGroupApi.addGroup(groupVO.getRealm(), groupVO.getGroupName());
    }

    @Override
    public void updateGroup(String groupId, AtyGroupVO groupVO) {
        keyCloakGroupApi.updateGroup(groupVO.getRealm(), groupId, groupVO.getGroupName());
    }

    @Override
    public void deleteGroup(String realm, String groupId) {
        keyCloakGroupApi.deleteGroup(realm, groupId);
    }

    @Override
    public AtyGroupInfoVO getGroup(String realm, String groupId) {
        return keyCloakGroupApi.groupDetail(realm, groupId);
    }

    @Override
    public PageResultVO<AtyGroupInfoVO> getUsers(AtyGroupParamVO groupParamVO) {
        return keyCloakGroupApi.groupList(groupParamVO.getRealm(), groupParamVO.getSearch(), groupParamVO.getPagination());
    }

    @Override
    public List<AtyGroupInfoVO> getUsers(String realm, String search) {
        return keyCloakGroupApi.groupList(realm, search);
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getGroupUsers(AtyGroupUserParamVO groupUserParamVO, String groupId) {
        return keyCloakGroupApi.groupUsers(groupUserParamVO.getRealm(),groupId,groupUserParamVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getGroupUsers(String realm, String groupId) {
        return keyCloakGroupApi.groupUsers(realm,groupId);
    }
}
