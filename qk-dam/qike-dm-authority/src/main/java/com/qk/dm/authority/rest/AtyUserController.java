package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.AtyAtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.vo.user.AtyUserResetPassWordVO;
import com.qk.dm.authority.vo.user.AtyUserVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理_用户
 *
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
     *
     * @param userVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult addUser(@RequestBody @Valid AtyAtyUserCreateVO userVO) {
        atyUserService.addUser(userVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户
     *
     * @param atyUserVO
     * @return DefaultCommonResult
     */
    @PutMapping("/{userId}")
    public DefaultCommonResult updateUser(@PathVariable String userId, @RequestBody @Valid AtyUserVO atyUserVO) {
        atyUserService.updateUser(userId, atyUserVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户_密码
     *
     * @param userVO
     * @return DefaultCommonResult
     */
    @PutMapping("/{userId}/password")
    public DefaultCommonResult resetPassword(@PathVariable String userId, @RequestBody @Valid AtyUserResetPassWordVO userVO) {
        atyUserService.resetPassword(userId, userVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户
     *
     * @param realm
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{userId}")
    public DefaultCommonResult deleteUser(String realm, @PathVariable String userId) {
        atyUserService.deleteUser(realm, userId);
        return DefaultCommonResult.success();
    }

    /**
     * 用户详情
     *
     * @param realm
     * @return DefaultCommonResult<AtyUserInfoVO>
     */
    @GetMapping("/{userId}")
    public DefaultCommonResult<AtyUserInfoVO> getUser(String realm, @PathVariable String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(realm, userId));
    }

    /**
     * 用户列表
     *
     * @param atyUserParamVO
     * @return DefaultCommonResult<PageResultVO < AtyUserInfoVO>>
     */
    @PostMapping("/page/list")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getUsers(@RequestBody AtyUserParamVO atyUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(atyUserParamVO));
    }

    /**
     * 用户列表-不分页
     *
     * @param realm
     * @param search
     * @return
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUsers(String realm, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(realm, search));
    }
}
