package com.qk.dm.authority.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.*;
import com.qk.dm.authority.vo.user.AtyUserResetPassWordVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 10:43
 * @since 1.0.0
 */
@Service
public class AtyUserServiceImpl implements AtyUserService {
    private final KeyCloakApi keyCloakApi;

    public AtyUserServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public void addUser(AtyAtyUserCreateVO userVO) {
        keyCloakApi.createUser(userVO.getRealm(), AtyUserMapper.INSTANCE.userInfo(userVO));
    }

    @Override
    public void updateUser(String userId, AtyUserVO atyUserVO) {
        AtyUserKeyCloakVO atyUserKeyCloakVO = AtyUserMapper.INSTANCE.userInfo(atyUserVO);
        atyUserKeyCloakVO.setId(userId);
        keyCloakApi.updateUser(atyUserVO.getRealm(), atyUserKeyCloakVO);
    }

    @Override
    public void deleteUser(String realm, String userId) {
        keyCloakApi.deleteUser(realm, userId);
    }

    @Override
    public void resetPassword(String userId, AtyUserResetPassWordVO userVO) {
        keyCloakApi.resetUserPassword(userVO.getRealm(), userId, userVO.getPassword());
    }

    @Override
    public AtyUserInfoVO getUser(String realm, String userId) {
        return keyCloakApi.userDetail(realm, userId);
    }

    @Override
    public PageResultVO<AtyUserInfoVO> getUsers(AtyUserParamVO atyUserParamVO) {
        return keyCloakApi.getUserList(atyUserParamVO.getRealm(), atyUserParamVO.getSearch(), atyUserParamVO.getPagination());
    }

    @Override
    public List<AtyUserInfoVO> getUsers(String realm, String search) {
        return keyCloakApi.getUserList(realm, search);
    }


}
