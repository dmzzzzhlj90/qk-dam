package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakRoleApi;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
@Service
public class AtyRoleServiceImpl implements AtyRoleService {
    private final KeyCloakRoleApi keyCloakRoleApi;
    private final EmpPowerService empPowerService;

    public AtyRoleServiceImpl(KeyCloakRoleApi keyCloakRoleApi, EmpPowerService empPowerService) {
        this.keyCloakRoleApi = keyCloakRoleApi;
        this.empPowerService = empPowerService;
    }

    @Override
    public void addClientRole(AtyClientRoleVO clientRoleVO) {
        keyCloakRoleApi.addClientRole(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName(), clientRoleVO.getDescription());
    }

    @Override
    public void updateClientRole(AtyClientRoleVO clientRoleVO) {
        keyCloakRoleApi.updateClientRole(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName(), clientRoleVO.getDescription());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClientRole(AtyClientRoleVO clientRoleVO) {
        //删除权限
        AtyClientRoleInfoVO clientRoleDetail = keyCloakRoleApi.clientRoleDetail(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
        empPowerService.deleteEmpPower(clientRoleDetail.getId());
        keyCloakRoleApi.deleteClientRole(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
    }

    @Override
    public AtyClientRoleInfoVO getClientRole(AtyClientRoleVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleDetail(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
    }

    @Override
    public PageResultVO<AtyClientRoleInfoVO> getClientRolesPage(AtyClientRoleParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleList(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getSearch(), clientRoleVO.getPagination());
    }

    @Override
    public List<AtyClientRoleInfoVO> getUsersRole(AtyClientRoleParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleList(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getSearch());
    }
}
