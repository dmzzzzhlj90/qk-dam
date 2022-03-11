package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
public interface AtyRoleService {
    void addClientRole(String realm, String roleName, String description);

    void updateClientRole(String realm, String roleName, String description);

    void deleteClientRole(String realm, String roleName);

    AtyClientRoleInfoVO getClientRole(String realm, String roleName);

    PageResultVO<AtyClientRoleInfoVO> getClientRoles(String realm, String search, Pagination pagination);

    List<AtyClientRoleInfoVO> getUsers(String realm, String search);

    PageResultVO<AtyUserInfoVO> getRoleUsers(String realm, String roleName, Pagination pagination);

    List<AtyUserInfoVO> getRoleUsers(String realm, String roleName);
}
