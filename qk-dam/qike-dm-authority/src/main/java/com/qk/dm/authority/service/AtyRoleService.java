package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
public interface AtyRoleService {
    void addClientRole(AtyClientRoleVO clientRoleVO);

    void updateClientRole(AtyClientRoleVO clientRoleVO);

    void deleteClientRole(AtyClientRoleVO clientRoleVO);

    AtyClientRoleInfoVO getClientRole(AtyClientRoleVO clientRoleVO);

    PageResultVO<AtyClientRoleInfoVO> getClientRolesPage(AtyClientRoleParamVO clientRoleParamVO);

    List<AtyClientRoleInfoVO> getUsersRole(AtyClientRoleParamVO clientRoleParamVO);
}
