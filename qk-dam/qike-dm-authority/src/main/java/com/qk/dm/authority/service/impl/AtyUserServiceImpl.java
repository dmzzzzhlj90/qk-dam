package com.qk.dm.authority.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.user.*;
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
    public void addUser(AtyUserCreateVO userVO) {
        keyCloakApi.createUser(userVO.getRealm(), AtyUserMapper.INSTANCE.userInfo(userVO));
    }

    @Override
    public void updateUser(AtyUserUpdateVO atyUserVO) {
        AtyUserKeyCloakVO atyUserKeyCloakVO = AtyUserMapper.INSTANCE.userInfo(atyUserVO);
        keyCloakApi.updateUser(atyUserVO.getRealm(), atyUserKeyCloakVO);
    }

    @Override
    public void deleteUser(String realm, String userId) {
        keyCloakApi.deleteUser(realm, userId);
    }

    @Override
    public void resetPassword(String realm, String userId, String password) {
        keyCloakApi.resetUserPassword(realm, userId, password);
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
