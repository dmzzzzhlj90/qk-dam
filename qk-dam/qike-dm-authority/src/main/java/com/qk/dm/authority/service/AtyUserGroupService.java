package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.group.AtyGroupBatchVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 11:07
 * @since 1.0.0
 */
public interface AtyUserGroupService {
    List<AtyGroupInfoVO> getUserGroup(String userId, String realm);

    void addUserGroup(String userId, String groupId, String realm);

    void addUserGroup( String groupId, AtyGroupBatchVO atyGroupBatchVO);

    void deleteUserGroup(String userId, String groupId, String realm);

}
