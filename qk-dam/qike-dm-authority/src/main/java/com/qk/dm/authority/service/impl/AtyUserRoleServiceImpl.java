package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.authority.common.keycloak.KeyCloakRoleApi;
import com.qk.dam.authority.common.keycloak.KeyCloakUserApi;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.*;
import com.qk.dm.authority.vo.user.AtyUserRoleFiltroVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/3/3 16:48
 * @since 1.0.0
 */
@Service
public class AtyUserRoleServiceImpl implements AtyUserRoleService {
    private final KeyCloakUserApi keyCloakUserApi;
    private final KeyCloakRoleApi keyCloakRoleApi;

    public AtyUserRoleServiceImpl(KeyCloakUserApi keyCloakUserApi, KeyCloakRoleApi keyCloakRoleApi) {
        this.keyCloakUserApi = keyCloakUserApi;
        this.keyCloakRoleApi = keyCloakRoleApi;
    }

    @Override
    public List<AtyClientRoleInfoVO> getUserClientRole(String realm, String userId,String client_clientId) {
        return keyCloakUserApi.userClientRole(realm, userId, client_clientId);
    }

    @Override
    public void addBatchByUsers(AtyUserClientRoleVO userClientRole) {
        keyCloakUserApi.addUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getName());
    }

    @Override
    public void deleteUserClientRole(AtyUserClientRoleVO userClientRole) {
        keyCloakUserApi.deleteUserClientRole(userClientRole.getRealm(), userClientRole.getClient_id(), userClientRole.getUserId(), userClientRole.getName());
    }

    @Override
    public void addBatchByUsers(AtyRoleBatchByUsersVO atyGroupBatchVO) {
            //根据角色id查已存在的用户全部解绑
            List<String> userIdList =dealAddUsers(atyGroupBatchVO);
            if (CollectionUtils.isNotEmpty(userIdList)){
                userIdList.forEach(userId -> keyCloakUserApi.addUserClientRole(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), userId, atyGroupBatchVO.getName()));
            }
    }

    /**
     * 根据角色id查已存在的用户全部解绑
     * @param atyGroupBatchVO
     * @return
     */
    private List<String> dealAddUsers(AtyRoleBatchByUsersVO atyGroupBatchVO) {
        //查询已授权用户-当前角色
        List<AtyUserInfoVO> userFiltro = keyCloakRoleApi.clientRoleUsers(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), atyGroupBatchVO.getName());
            //获取需要解绑的用户id
            List<String> unboundIdList =CollectionUtils.isEmpty(userFiltro) ? new ArrayList<>() : unboundIdList(userFiltro,atyGroupBatchVO);
            //获取需要绑定的用户id
            List<String> bindingList = CollectionUtils.isEmpty(atyGroupBatchVO.getUserIds()) ? new ArrayList<>() : bindingIdList(userFiltro,atyGroupBatchVO);
            if (CollectionUtils.isEmpty(unboundIdList)){
                //解绑用户
                unboundIdList.forEach(userId->{
                    keyCloakUserApi.deleteUserClientRole(atyGroupBatchVO.getRealm(), atyGroupBatchVO.getClient_id(), userId, atyGroupBatchVO.getName());
                });
            }
        return bindingList;
    }

    /**
     * 获取绑定的用户id
     * @param userFiltro
     * @param atyGroupBatchVO
     * @return
     */
    private List<String> bindingIdList(List<AtyUserInfoVO> userFiltro, AtyRoleBatchByUsersVO atyGroupBatchVO) {
        //当查询橘色绑定用户为空
        if (CollectionUtils.isEmpty(userFiltro)){
            return atyGroupBatchVO.getUserIds();
        }else{
            List<String> idList = userFiltro.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
            atyGroupBatchVO.getUserIds().forEach(id->{
                if (idList.contains(id)){
                    atyGroupBatchVO.getUserIds().remove(id);
                }
            });
            return  atyGroupBatchVO.getUserIds();
        }
    }

    /**
     * 获取需要解绑的用户id
     * @param userFiltro
     * @param atyGroupBatchVO
     * @return
     */
    private List<String> unboundIdList(List<AtyUserInfoVO> userFiltro, AtyRoleBatchByUsersVO atyGroupBatchVO) {
        if (CollectionUtils.isEmpty(atyGroupBatchVO.getUserIds())){
            return userFiltro.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
        }else{
            userFiltro.forEach(userInfoVO -> {
                if (atyGroupBatchVO.getUserIds().contains(userInfoVO.getId())){
                    userFiltro.remove(userInfoVO);
                }
            });
         return userFiltro.stream().map(AtyUserInfoVO::getId).collect(Collectors.toList());
        }
    }

    @Override
    public void addBatchByRoles(AtyRoleBatchByRolesVO batchByRolesVO) {
        batchByRolesVO.getNames().forEach(roleName -> keyCloakUserApi.addUserClientRole(batchByRolesVO.getRealm(), batchByRolesVO.getClient_id(), batchByRolesVO.getUserId(), roleName));
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getRoleUsersPage(AtyClientRoleUserParamVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleUsers(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName(),clientRoleVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getRoleUsers(AtyRoleUserFiltroVO clientRoleVO) {
        return keyCloakRoleApi.clientRoleUsers(clientRoleVO.getRealm(), clientRoleVO.getClient_id(), clientRoleVO.getName());
    }

    @Override
    public List<AtyUserInfoVO> getUsersFiltro(AtyRoleUserFiltroVO roleUserFiltroVO) {
        //查询所有用户
        List<AtyUserInfoVO> users = keyCloakUserApi.userList(roleUserFiltroVO.getRealm(), roleUserFiltroVO.getSearch());
        //查询已授权用户
        List<AtyUserInfoVO> userFiltro = keyCloakRoleApi.clientRoleUsers(roleUserFiltroVO.getRealm(), roleUserFiltroVO.getClient_id(), roleUserFiltroVO.getName());
        //所有用户排除已授权用户
        users.removeAll(userFiltro);
        return users;
    }

    @Override
    public List<AtyClientRoleInfoVO> getRolesFiltro(AtyUserRoleFiltroVO userRoleFiltroVO) {
        //查询所有角色
        List<AtyClientRoleInfoVO> roles = keyCloakRoleApi.clientRoleList(userRoleFiltroVO.getRealm(), userRoleFiltroVO.getClient_id(), userRoleFiltroVO.getSearch());
        //查询已授权角色
        List<AtyClientRoleInfoVO> roleFiltro = keyCloakUserApi.userClientRole(userRoleFiltroVO.getRealm(), userRoleFiltroVO.getUserId(), userRoleFiltroVO.getClient_clientId());
        if(roleFiltro != null) {
            List<String> ids = roleFiltro.stream().map(AtyClientRoleInfoVO::getId).collect(Collectors.toList());
            return roles.stream().filter(role -> !ids.contains(role.getId())).collect(Collectors.toList());
        }
        return roles;
    }
}
