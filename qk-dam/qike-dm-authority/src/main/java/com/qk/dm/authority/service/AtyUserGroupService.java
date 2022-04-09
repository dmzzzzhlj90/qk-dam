package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.group.*;
import com.qk.dm.authority.vo.user.AtyUserGroupFiltroVO;
import com.qk.dm.authority.vo.user.AtyUserGroupParamVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 11:07
 * @since 1.0.0
 */
public interface AtyUserGroupService {
    /**
     * 已绑定的用户组列表
     * @param userGroupParamVO
     * @param userId
     * @return
     */
    PageResultVO<AtyGroupInfoVO> getUserGroup(AtyUserGroupParamVO userGroupParamVO, String userId);

    List<AtyGroupInfoVO> getUserGroup(String realm, String userId);

    void addBatchByUsers(AtyUserGroupVO atyUserGroupVO);

    /**
     * 单个解绑
     * @param atyUserGroupVO
     */
    void deleteUserGroup(AtyUserGroupVO atyUserGroupVO);

    /**
     * 批量绑定用户
     * @param atyGroupBatchByUsersVO
     */
    void addBatchByUsers(AtyGroupBatchByUsersVO atyGroupBatchByUsersVO);

    /**
     * 批量绑定用户组
     * @param batchByGroupsVO
     */
    void addBatchByGroups(AtyGroupBatchByGroupsVO batchByGroupsVO);

    /**
     * 已绑定用户列表
     * @param groupUserParamVO
     * @param groupId
     * @return
     */
    PageResultVO<AtyUserInfoVO> getGroupUsers(AtyGroupUserParamVO groupUserParamVO, String groupId);

    /**
     * 已绑定的用户列表-不分页
     * @param realm
     * @param groupId
     * @return
     */
    List<AtyUserInfoVO> getGroupUsers(String realm, String groupId);

    /**
     * 排除已授权的用户列表
     * @param userFiltroVO
     * @return
     */
    List<AtyUserInfoVO> getUserFiltro(AtyGroupUserFiltroVO userFiltroVO);

    /**
     * 排除已绑定的用户组列表
     * @param groupFiltroVO
     * @return
     */
    List<AtyGroupInfoVO> getGroupFiltro(AtyUserGroupFiltroVO groupFiltroVO);
}
