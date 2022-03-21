package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.clientrole.*;
import com.qk.dm.authority.vo.user.AtyUserRoleFiltroVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 16:48
 * @since 1.0.0
 */
public interface AtyUserRoleService {
    /**
     * 已绑定的角色列表
     * @param realm
     * @param userId
     * @param client_clientId
     * @return
     */
    List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId,String client_clientId);

    void addBatchByUsers(AtyUserClientRoleVO userClientRole);

    /**
     * 单个解绑
     * @param userClientRole
     */
    void deleteUserClientRole(AtyUserClientRoleVO userClientRole);

    /**
     * 批量绑定用户
     * @param atyGroupBatchVO
     */
    void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO);

    /**
     * 批量绑定角色
     * @param batchByRolesVO
     */
    void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO);

    /**
     * 已授权的用户列表
     * @param clientRoleVO
     * @return
     */
    PageResultVO<AtyUserInfoVO> getRoleUsersPage(AtyClientRoleUserParamVO clientRoleVO);

    /**
     * 已授权的用户列表-不分页
     * @param clientRoleVO
     * @return
     */
    List<AtyUserInfoVO> getRoleUsers(AtyRoleUserFiltroVO clientRoleVO);

    /**
     * 排除已授权的用户列表
     * @param roleUserFiltroVO
     * @return
     */
    List<AtyUserInfoVO> getUsersFiltro(AtyRoleUserFiltroVO roleUserFiltroVO);

    /**
     * 排除已添加的角色列表
     * @param userRoleFiltroVO
     * @return
     */
    List<AtyClientRoleInfoVO> getRolesFiltro(AtyUserRoleFiltroVO userRoleFiltroVO);
}
