package com.qk.dm.authority.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:44
 * @since 1.0.0
 */
public interface AtyRoleService {
    void addClientRole(String realm, String client_id, String roleName, String description);

    void updateClientRole(String realm, String client_id, String roleName, String description);

    void deleteClientRole(String realm, String client_id, String roleName);

    AtyClientRoleInfoVO getClientRole(String realm, String client_id, String roleName);

    PageResultVO<AtyClientRoleInfoVO> getClientRoles(String realm,String client_id, String search, Pagination pagination);

    List<AtyClientRoleInfoVO> getUsers(String realm, String client_id, String search);
}
