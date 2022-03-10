package com.qk.dam.authority.common.keycloak;

import com.qk.dam.authority.common.mapstruct.AtyGroupMapper;
import com.qk.dam.authority.common.mapstruct.AtyUserMapper;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/9 17:33
 * @since 1.0.0
 */
@Service
public class KeyCloakGroupApi {
    private final KeyCloakApi keyCloakApi;

    public KeyCloakGroupApi(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    /**
     * 创建分组
     *
     * @param realm
     * @param groupName
     */
    public void addGroup(String realm, String groupName) {
        keyCloakApi.addGroup(realm, groupName);
    }

    /**
     * 修改分组
     *
     * @param realm
     * @param groupId
     * @param groupName
     */
    public void updateGroup(String realm, String groupId, String groupName) {
        keyCloakApi.updateGroup(realm, groupId, groupName);
    }

    /**
     * 删除分组
     *
     * @param realm
     * @param groupId
     */
    public void deleteGroup(String realm, String groupId) {
        keyCloakApi.deleteGroup(realm, groupId);
    }

    /**
     * 分组详情
     *
     * @param groupId
     * @return
     */
    public AtyGroupInfoVO groupDetail(String realm, String groupId) {
        return AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.groupDetail(realm, groupId));
    }

    /**
     * 查询组下的所有用户
     * @param realm
     * @param groupId
     * @return
     */
    public List<AtyUserInfoVO> groupUsers(String realm, String groupId) {
        return AtyUserMapper.INSTANCE.userInfo(keyCloakApi.getGroupUsers(realm, groupId));
    }

    /**
     * 查询所有分组
     */
    public PageResultVO<AtyGroupInfoVO> groupList(String realm, String search, Pagination pagination) {
        return new PageResultVO<>(
                keyCloakApi.groupList(realm, search).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.groupList(realm, search, pagination)));
    }

    /**
     * 查询所有分组
     */
    public List<AtyGroupInfoVO> groupList(String realm, String search){
        return AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.groupList(realm, search));
    }

    /**
     * 用户分组列表
     *
     * @param userId
     */
    public List<AtyGroupInfoVO> userGroup(String realm, String userId) {
        return AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.userGroup(realm, userId));
    }

    /**
     * 用户添加分组
     */
    public void addUserGroup(String realm, String userId, String groupId) {
        keyCloakApi.addUserGroup(realm, userId, groupId);
    }

    /**
     * 用户离开分组
     */
    public void deleteUserGroup(String realm, String userId, String groupId) {
        keyCloakApi.deleteUserGroup(realm, userId, groupId);
    }
}
