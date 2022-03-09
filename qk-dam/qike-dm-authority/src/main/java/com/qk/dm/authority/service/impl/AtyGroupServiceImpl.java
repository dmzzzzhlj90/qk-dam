package com.qk.dm.authority.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 17:44
 * @since 1.0.0
 */
@Service
public class AtyGroupServiceImpl implements AtyGroupService {

    private final KeyCloakApi keyCloakApi;

    public AtyGroupServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public void addGroup(AtyGroupVO groupVO) {
        keyCloakApi.addGroup(groupVO.getRealm(), groupVO.getGroupName());
    }

    @Override
    public void updateGroup(String groupId, AtyGroupVO groupVO) {
        keyCloakApi.updateGroup(groupVO.getRealm(), groupId, groupVO.getGroupName());
    }

    @Override
    public void deleteGroup(String realm, String groupId) {
        keyCloakApi.deleteGroup(realm, groupId);
    }

    @Override
    public AtyGroupInfoVO getGroup(String realm, String groupId) {
        return keyCloakApi.groupDetail(realm, groupId);
    }

    @Override
    public PageResultVO<AtyGroupInfoVO> getUsers(AtyGroupParamVO groupParamVO) {
        return keyCloakApi.groupList(groupParamVO.getRealm(), groupParamVO.getSearch(), groupParamVO.getPagination());
    }

    @Override
    public List<AtyGroupInfoVO> getUsers(String realm, String search) {
        return keyCloakApi.groupList(realm, search);
    }

    @Override
    public List<AtyUserInfoVO> getGroupUsers(String realm, String groupId) {
        return keyCloakApi.getGroupUsers(realm,groupId);
    }
}
