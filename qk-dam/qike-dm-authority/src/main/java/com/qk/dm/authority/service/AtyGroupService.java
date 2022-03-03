package com.qk.dm.authority.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 17:43
 * @since 1.0.0
 */
public interface AtyGroupService {
    void addGroup(AtyGroupVO groupVO);

    void updateGroup(String groupId, AtyGroupVO groupVO);

    void deleteGroup(String groupId, String realm);

    AtyGroupInfoVO getGroup(String realm, String groupId);

    PageResultVO<AtyGroupInfoVO> getUsers(AtyGroupParamVO groupParamVO);

    List<AtyGroupInfoVO> getUsers(String realm, String search);
}
