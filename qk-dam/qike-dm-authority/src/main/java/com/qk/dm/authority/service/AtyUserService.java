package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 10:42
 * @since 1.0.0
 */
public interface AtyUserService {
    void addUser(AtyUserCreateVO userVO);

    void updateUser(String userId, AtyUserUpdateVO atyUserVO);

    void deleteUser(String realm, String userId);

    void resetPassword(String realm, String userId, String password);

    AtyUserInfoVO getUser(String realm, String userId);

    PageResultVO<AtyUserInfoVO> getUsers(AtyUserParamVO atyUserParamVO);

    List<AtyUserInfoVO> getUsers(String realm, String search);

    void saveAllUsers(List<AtyUserInputExceVO> userlist, String relame);
}
