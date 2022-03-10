package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserParamVO;
import com.qk.dm.authority.vo.user.AtyUserResetPassWordVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 权限管理_用户
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/users")
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
    public DefaultCommonResult addUser(@RequestBody @Valid AtyUserCreateVO userVO) {
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
    public DefaultCommonResult updateUser(@PathVariable String userId, @RequestBody @Valid AtyUserUpdateVO atyUserVO) {
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
        atyUserService.resetPassword(userVO.getRealm(), userId, userVO.getPassword());
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户
     *
     * @param userId
     * @param realm
     * @return
     */
    @DeleteMapping("/{userId}")
    public DefaultCommonResult deleteUser(@Valid @NotBlank String realm, @PathVariable String userId) {
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
    public DefaultCommonResult<AtyUserInfoVO> getUser(@Valid @NotBlank String realm, @PathVariable String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(realm, userId));
    }

    /**
     * 用户列表
     *
     * @param atyUserParamVO
     * @return DefaultCommonResult<PageResultVO < AtyUserInfoVO>>
     */
    @PostMapping("/page")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getUsersPage(@RequestBody @Valid AtyUserParamVO atyUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(atyUserParamVO));
    }

    /**
     * 用户列表-不分页
     *
     * @param atyUserParamVO
     * @return
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUsers(@Valid AtyUserParamVO atyUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(atyUserParamVO.getRealm(), atyUserParamVO.getSearch()));
    }
}
