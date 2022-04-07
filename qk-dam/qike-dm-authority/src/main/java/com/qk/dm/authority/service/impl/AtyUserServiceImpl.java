package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakUserApi;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 10:43
 * @since 1.0.0
 */
@Service
public class AtyUserServiceImpl implements AtyUserService {
    private final KeyCloakUserApi keyCloakUserApi;
    private final EmpPowerService empPowerService;

    public AtyUserServiceImpl(KeyCloakUserApi keyCloakUserApi, EmpPowerService empPowerService) {
        this.keyCloakUserApi = keyCloakUserApi;
        this.empPowerService = empPowerService;
    }

    @Override
    public void addUser(AtyUserCreateVO userVO) {
        keyCloakUserApi.createUser(userVO.getRealm(), AtyUserMapper.INSTANCE.userInfo(userVO));
    }

    @Override
    public void updateUser(String userId, AtyUserUpdateVO atyUserVO) {
        AtyUserKeyCloakVO atyUserKeyCloakVO = AtyUserMapper.INSTANCE.userInfo(atyUserVO);
        atyUserKeyCloakVO.setId(userId);
        keyCloakUserApi.updateUser(atyUserVO.getRealm(), atyUserKeyCloakVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String realm, String userId) {
        //删除权限
        empPowerService.deleteEmpPower(userId);
        keyCloakUserApi.deleteUser(realm, userId);
    }

    @Override
    public void resetPassword(String realm, String userId, String password) {
        keyCloakUserApi.resetUserPassword(realm, userId, password);
    }

    @Override
    public AtyUserInfoVO getUser(String realm, String userId) {
        return keyCloakUserApi.userDetail(realm, userId);
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getUsers(AtyUserParamVO atyUserParamVO) {
        return keyCloakUserApi.userList(atyUserParamVO.getRealm(), atyUserParamVO.getSearch(), atyUserParamVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getUsers(String realm, String search) {
        return keyCloakUserApi.userList(realm, search);
    }

    @Override
    public void saveAllUsers(List<AtyUserInputExceVO> userlist, String relame) {
        keyCloakUserApi.saveAllUsers(userlist,relame);
    }

}
