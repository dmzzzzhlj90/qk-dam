package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.user.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @PutMapping("")
    public DefaultCommonResult updateUser(@RequestBody @Valid AtyUserUpdateVO atyUserVO) {
        atyUserService.updateUser(atyUserVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户_密码
     *
     * @param userVO
     * @return DefaultCommonResult
     */
    @PutMapping("/password")
    public DefaultCommonResult resetPassword(@RequestBody @Valid AtyUserResetPassWordVO userVO) {
        atyUserService.resetPassword(userVO.getRealm(),userVO.getId(), userVO.getPassword());
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户
     *
     * @param userVO
     * @return DefaultCommonResult
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteUser(@RequestBody @Valid AtyUserDeleteVO userVO) {
        atyUserService.deleteUser(userVO.getRealm(), userVO.getId());
        return DefaultCommonResult.success();
    }

    /**
     * 用户详情
     *
     * @param realm
     * @return DefaultCommonResult<AtyUserInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<AtyUserInfoVO> getUser(@Valid @NotNull String realm, @PathVariable String id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(realm, id));
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
