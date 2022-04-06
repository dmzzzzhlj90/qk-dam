package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakGroupApi;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 17:44
 * @since 1.0.0
 */
@Service
public class AtyGroupServiceImpl implements AtyGroupService {

    private final KeyCloakGroupApi keyCloakGroupApi;
    private final EmpPowerService empPowerService;

    public AtyGroupServiceImpl(KeyCloakGroupApi keyCloakGroupApi, EmpPowerService empPowerService) {
        this.keyCloakGroupApi = keyCloakGroupApi;
        this.empPowerService = empPowerService;
    }

    @Override
    public void addGroup(AtyGroupVO groupVO) {
        keyCloakGroupApi.addGroup(groupVO.getRealm(), groupVO.getName());
    }

    @Override
    public void updateGroup(String groupId, AtyGroupVO groupVO) {
        keyCloakGroupApi.updateGroup(groupVO.getRealm(), groupId, groupVO.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(String realm, String groupId) {
        //删除权限,先删除权限可控制回滚
        empPowerService.deleteEmpPower(groupId);
        keyCloakGroupApi.deleteGroup(realm, groupId);
    }

    @Override
    public AtyGroupInfoVO getGroup(String realm, String groupId) {
        return keyCloakGroupApi.groupDetail(realm, groupId);
    }

    @Override
    public PageResultVO<AtyGroupInfoVO> getGroupPage(AtyGroupParamVO groupParamVO) {
        return keyCloakGroupApi.groupList(groupParamVO.getRealm(), groupParamVO.getSearch(), groupParamVO.getPagination());
    }

    @Override
    public List<AtyGroupInfoVO> getGroupList(String realm,String search) {
        return keyCloakGroupApi.groupList(realm, search);
    }
}
