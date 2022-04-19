package com.qk.dam.authority.common.keycloak;

import com.qk.dam.authority.common.mapstruct.AtyGroupMapper;
import com.qk.dam.authority.common.mapstruct.AtyRoleMapper;
import com.qk.dam.authority.common.mapstruct.AtyUserMapper;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/9 17:33
 * @since 1.0.0
 */
@Service
public class KeyCloakUserApi {
    private final KeyCloakApi keyCloakApi;

    public KeyCloakUserApi(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    /**
     * 创建用户
     *
     * @param realm
     * @param userVO
     */
    public void createUser(String realm, AtyUserKeyCloakVO userVO) {
        UserRepresentation user = AtyUserMapper.INSTANCE.userInfo(userVO);
        keyCloakApi.createUser(realm,userVO.getPassword(),user);
    }

    /**
     * 修改用户信息
     *
     * @param realm
     * @param userVO
     */
    public void updateUser(String realm, AtyUserKeyCloakVO userVO) {
        //todo 为了keycolakapi与mapstruct解偶，不使用mapstruct
        keyCloakApi.updateUser(
                realm,
                userVO.getId(),
                userVO.getEmail(),
                userVO.getFirstName(),
                userVO.getLastName(),
                userVO.getEnabled());
    }

    /**
     * 删除用户
     *
     * @param realm
     * @param userId
     */
    public void deleteUser(String realm, String userId) {
        keyCloakApi.deleteUser(realm, userId);
    }

    /**
     * 重置密码
     *
     * @param realm
     * @param userId
     * @param password
     */
    public void resetUserPassword(String realm, String userId, String password) {
        keyCloakApi.resetUserPassword(realm, userId, password);
    }

    /**
     * 用户详情
     *
     * @param realm
     * @param userId
     * @return
     */
    public AtyUserInfoVO userDetail(String realm, String userId) {
        AtyUserInfoVO atyUserInfoVO = AtyUserMapper.INSTANCE.userInfo(keyCloakApi.userDetail(realm, userId));
        //用户分组
//        atyUserInfoVO.setGroupList(userGroup(realm, userId));
        return atyUserInfoVO;
    }

    /**
     * 用户列表
     *
     * @param realm
     * @param search
     * @param pagination
     * @return
     */
    public PageResultVO<AtyUserInfoVO> userList(String realm, String search, Pagination pagination) {
        return new PageResultVO<>(
                (long) keyCloakApi.getUserCount(realm, search),
                pagination.getPage(),
                pagination.getSize(),
                AtyUserMapper.INSTANCE.userInfo(keyCloakApi.getUserList(realm, search, pagination)));
    }

    /**
     * 用户列表
     *
     * @param realm
     * @param search
     * @return
     */
    public List<AtyUserInfoVO> userList(String realm, String search) {
        return AtyUserMapper.INSTANCE.userInfo(keyCloakApi.getUserList(realm, search));
    }

    /**
     * 导入添加用户信息
     * @param userlist
     * @param relame
     */
    public void saveAllUsers(List<AtyUserInputExceVO> userlist, String relame) {
        userlist.forEach(atyUserInputExceVO -> {
            UserRepresentation user = AtyUserMapper.INSTANCE.userExcelInfo(atyUserInputExceVO);
            keyCloakApi.createUser(relame,atyUserInputExceVO.getPassword(),user);
        });
    }

    /**
     * 用户分组列表
     * @param realm
     * @param userId
     * @param pagination
     * @return
     */
    public PageResultVO<AtyGroupInfoVO> userGroup(String realm, String userId, Pagination pagination) {
        return new PageResultVO<>(
                keyCloakApi.userGroup(realm, userId).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.userGroup(realm, userId,pagination)));
    }

    /**
     * 用户分组列表
     * @param realm
     * @param userId
     * @return
     */
    public List<AtyGroupInfoVO> userGroup(String realm, String userId) {
        return AtyGroupMapper.INSTANCE.userGroup(keyCloakApi.userGroup(realm, userId));
    }

    /**
     * 用户添加分组
     * @param realm
     * @param userId
     * @param groupId
     */
    public void addUserGroup(String realm, String userId, String groupId) {
        keyCloakApi.addUserGroup(realm, userId, groupId);
    }

    /**
     * 用户离开分组
     * @param realm
     * @param userId
     * @param groupId
     */
    public void deleteUserGroup(String realm, String userId, String groupId) {
        keyCloakApi.deleteUserGroup(realm, userId, groupId);
    }

    /**
     * 用户的客户端角色列表
     * @param realm
     * @param userId
     * @param client_clientId
     * @return
     */
    public List<AtyClientRoleInfoVO> userClientRole(String realm, String userId, String client_clientId) {
        return AtyRoleMapper.INSTANCE.userRole(keyCloakApi.userClientRole(realm, userId, client_clientId));
    }

    /**
     * 用户添加客户端角色
     * @param realm
     * @param client_id
     * @param userId
     * @param roleName
     */
    public void addUserClientRole(String realm, String client_id, String userId, String roleName) {
        keyCloakApi.addUserClientRole(realm, client_id, userId, roleName);
    }

    /**
     * 用户删除客户端角色
     * @param realm
     * @param client_id
     * @param userId
     * @param roleName
     */
    public void deleteUserClientRole(String realm, String client_id, String userId, String roleName) {
        keyCloakApi.deleteUserClientRole(realm, client_id, userId, roleName);
    }
}
