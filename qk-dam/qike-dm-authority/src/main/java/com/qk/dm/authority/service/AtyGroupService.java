package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;

/**
 * @author shenpj
 * @date 2022/3/2 17:43
 * @since 1.0.0
 */
public interface AtyGroupService {
    void addGroup(AtyGroupVO groupVO);

    void updateGroup(String groupId, AtyGroupVO groupVO);

    void deleteGroup(String realm, String groupId);

    AtyGroupInfoVO getGroup(String realm, String groupId);

    PageResultVO<AtyGroupInfoVO> getGroupPage(AtyGroupParamVO groupParamVO);
}
