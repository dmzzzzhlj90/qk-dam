package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.group.AtyGroupCreateVO;

/**
 * @author shenpj
 * @date 2022/3/2 17:43
 * @since 1.0.0
 */
public interface AtyGroupService {
    void addGroup(AtyGroupCreateVO groupVO);

    void updateGroup(String groupId, AtyGroupCreateVO groupVO);

    void deleteGroup(String groupId, String realm);
}
