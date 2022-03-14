package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import com.qk.dm.authority.vo.user.AtyUserGroupParamVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 11:07
 * @since 1.0.0
 */
public interface AtyUserGroupService {
    PageResultVO<AtyGroupInfoVO> getUserGroup(AtyUserGroupParamVO userGroupParamVO, String userId);

    List<AtyGroupInfoVO> getUserGroup(String realm, String userId);

    void addBatchByUsers(AtyUserGroupVO atyUserGroupVO);

    void deleteUserGroup(AtyUserGroupVO atyUserGroupVO);

    void addBatchByUsers(AtyGroupBatchByUsersVO atyGroupBatchByUsersVO);

    void addBatchByGroups(AtyGroupBatchByGroupsVO batchByGroupsVO);
}