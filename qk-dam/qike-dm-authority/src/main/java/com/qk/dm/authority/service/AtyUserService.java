package com.qk.dm.authority.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.*;
import com.qk.dm.authority.vo.user.AtyAtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 10:42
 * @since 1.0.0
 */
public interface AtyUserService {
    void addUser(AtyAtyUserCreateVO userVO);

    void updateUser(String userId, AtyUserVO atyUserVO);

    void deleteUser(String realm, String userId);

    void resetPassword(String userId, AtyUserResetPassWordVO userVO);

    AtyUserInfoVO getUser(String realm, String userId);

    PageResultVO<AtyUserInfoVO> getUsers(AtyUserParamVO atyUserParamVO);

    List<AtyUserInfoVO> getUsers(String realm, String search);
}
