package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 16:48
 * @since 1.0.0
 */
public interface AtyUserRoleService {
    List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId, String client_clientId);

    void addBatchByUsers(AtyUserClientRoleVO userClientRole);

    void deleteUserClientRole(AtyUserClientRoleVO userClientRole);

    void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO);

    void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO);
}
