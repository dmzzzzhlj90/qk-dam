package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakRoleApi;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleUserParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;
import org.springframework.stereotype.Service;

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
    public void deleteClientRole(AtyClientRoleVO clientRoleVO) {
        AtyClientRoleInfoVO clientRoleDetail = keyCloakRoleApi.clientRoleDetail(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
        keyCloakRoleApi.deleteClientRole(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
        //删除权限
        empPowerService.deleteEmpPower(clientRoleDetail.getId());
    }

    @Override
    public AtyClientRoleInfoVO getClientRole(AtyClientRoleVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleDetail(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
    }

    @Override
    public PageResultVO<AtyClientRoleInfoVO> getClientRoles(AtyClientRoleParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleList(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getSearch(), clientRoleVO.getPagination());
    }

    @Override
    public List<AtyClientRoleInfoVO> getUsers(AtyClientRoleParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleList(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getSearch());
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getRoleUsersPage(AtyClientRoleUserParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleUsers(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName(),clientRoleVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getRoleUsers(AtyClientRoleUserParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleUsers(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
    }
}
