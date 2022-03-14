package com.qk.dam.authority.common.keycloak;

import com.qk.dam.authority.common.mapstruct.AtyRoleMapper;
import com.qk.dam.authority.common.mapstruct.AtyUserMapper;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/9 17:33
 * @since 1.0.0
 */
@Service
public class KeyCloakRoleApi {
    private final KeyCloakApi keyCloakApi;

    public KeyCloakRoleApi(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    /**
     * 新增客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @param description
     */
    public void addClientRole(String realm, String client_id, String roleName, String description) {
        keyCloakApi.addClientRole(realm, client_id, roleName, description);
    }

    /**
     * 修改客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @param description
     */
    public void updateClientRole(String realm, String client_id, String roleName, String description) {
        keyCloakApi.updateClientRole(realm, client_id, roleName, description);
    }

    /**
     * 删除客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     */
    public void deleteClientRole(String realm, String client_id, String roleName) {
        keyCloakApi.deleteClientRole(realm, client_id, roleName);
    }

    /**
     * 客户端角色详情
     * @param realm
     * @param client_id
     * @param roleName
     * @return
     */
    public AtyClientRoleInfoVO clientRoleDetail(String realm, String client_id, String roleName) {
        AtyClientRoleInfoVO atyClientRoleInfoVO = AtyRoleMapper.INSTANCE.userRole(keyCloakApi.clientRoleDetail(realm, client_id, roleName));
        atyClientRoleInfoVO.setMembers(clientRoleUsers(realm, client_id, roleName));
        return atyClientRoleInfoVO;
    }

    /**
     * 客户端所有角色
     * @param realm
     * @param client_id
     * @param search
     * @param pagination
     * @return
     */
    public PageResultVO<AtyClientRoleInfoVO> clientRoleList(String realm, String client_id, String search, Pagination pagination) {
        return new PageResultVO<>(
                keyCloakApi.clientRoleList(realm, client_id, search).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyRoleMapper.INSTANCE.userRole(keyCloakApi.clientRoleList(realm, client_id, search, pagination)));
    }

    /**
     * 客户端所有角色
     * @param realm
     * @param client_id
     * @param search
     * @return
     */
    public List<AtyClientRoleInfoVO> clientRoleList(String realm, String client_id, String search) {
        return AtyRoleMapper.INSTANCE.userRole(keyCloakApi.clientRoleList(realm, client_id, search));
    }

    /**
     * 客户端角色下所有用户
     * @param realm
     * @param client_id
     * @param roleName
     * @return
     */
    public PageResultVO<AtyUserInfoVO> clientRoleUsers(String realm, String client_id, String roleName, Pagination pagination) {
        return new PageResultVO<>(
                keyCloakApi.clientRoleUsers(realm, client_id, roleName).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyUserMapper.INSTANCE.userInfo(new ArrayList<>(keyCloakApi.clientRoleUsers(realm, client_id, roleName,pagination))));
    }

    /**
     * 客户端角色下所有用户
     * @param realm
     * @param client_id
     * @param roleName
     * @return
     */
    public List<AtyUserInfoVO> clientRoleUsers(String realm, String client_id, String roleName) {
        return AtyUserMapper.INSTANCE.userInfo(new ArrayList<>(keyCloakApi.clientRoleUsers(realm, client_id, roleName)));
    }
}
