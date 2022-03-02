package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.params.UserParamVO;
import com.qk.dm.authority.vo.user.AtyAtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.vo.user.AtyUserResetPassWordVO;
import com.qk.dm.authority.vo.user.AtyUserVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 权限管理_用户
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class AtyUserController {
    private final AtyUserService atyUserService;

    public AtyUserController(AtyUserService atyUserService) {
        this.atyUserService = atyUserService;
    }

    /**
     * 新增用户
     * @param userVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addUser(@RequestBody @Valid AtyAtyUserCreateVO userVO) {
        atyUserService.addUser(userVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户
     * @param userId
     * @param atyUserVO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult updateUser(@PathVariable("id") String userId,@RequestBody @Valid AtyUserVO atyUserVO) {
        atyUserService.updateUser(userId, atyUserVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户_密码
     * @param userId
     * @param userVO
     * @return
     */
    @PutMapping("/{id}/password")
    public DefaultCommonResult resetPassword(@PathVariable("id") String userId,@RequestBody @Valid AtyUserResetPassWordVO userVO) {
        atyUserService.resetPassword(userId,userVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户
     * @param realm
     * @param userId
     * @return
     */
    @DeleteMapping("/{id}")
    public DefaultCommonResult deleteUser(String realm, @PathVariable("id") String userId) {
        atyUserService.deleteUser(realm, userId);
        return DefaultCommonResult.success();
    }

    /**
     * 用户详情
     * @param realm
     * @param userId
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<AtyUserInfoVO> getUser(String realm, @PathVariable("id") String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(realm, userId));
    }

    /**
     * 用户列表
     * @param userParamVO
     * @return
     */
    @PostMapping("/list")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getUsers(@RequestBody UserParamVO userParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(userParamVO));
    }
}
