package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import com.qk.dm.authority.vo.user.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理_用户管理
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/users")
public class AtyUserController {
    private final AtyUserService atyUserService;
    private final AtyUserGroupService atyUserGroupService;
    private final AtyUserRoleService atyUserRoleService;

    public AtyUserController(AtyUserService atyUserService, AtyUserGroupService atyUserGroupService, AtyUserRoleService atyUserRoleService) {
        this.atyUserService = atyUserService;
        this.atyUserGroupService = atyUserGroupService;
        this.atyUserRoleService = atyUserRoleService;
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
        atyUserService.updateUser(atyUserVO.getUserId(), atyUserVO);
        return DefaultCommonResult.success();
    }

    /**
     * 重置密码
     *
     * @param userVO
     * @return DefaultCommonResult
     */
    @PutMapping("/password")
    public DefaultCommonResult resetPassword(@RequestBody @Valid AtyUserResetPassWordVO userVO) {
        atyUserService.resetPassword(userVO.getRealm(), userVO.getUserId(), userVO.getPassword());
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户
     * 注释：因openApi不支持DeleteMapping带RequestBody
     * @param userDetailVO
     * @return DefaultCommonResult
     */
    @PostMapping("/delete")
    public DefaultCommonResult deleteUser(@RequestBody @Valid AtyUserDetailVO userDetailVO) {
        atyUserService.deleteUser(userDetailVO.getRealm(), userDetailVO.getUserId());
        return DefaultCommonResult.success();
    }

    /**
     * 用户详情
     *
     * @param userDetailVO
     * @return DefaultCommonResult<AtyUserInfoVO>
     */
    @GetMapping("")
    public DefaultCommonResult<AtyUserInfoVO> getUser(@Valid AtyUserDetailVO userDetailVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(userDetailVO.getRealm(), userDetailVO.getUserId()));
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
     * 用户组/角色详情-添加授权-用户列表
     *
     * @param atyUserParamVO
     * @return DefaultCommonResult<List < AtyUserInfoVO>>
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUsers(@Valid AtyUserParamVO atyUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(atyUserParamVO.getRealm(), atyUserParamVO.getSearch()));
    }

    /**
     * 已绑定的用户组列表
     *
     * @param userGroupParamVO
     * @return DefaultCommonResult<PageResultVO < AtyGroupInfoVO>>
     */
    @PostMapping("/groups/page")
    public DefaultCommonResult<PageResultVO<AtyGroupInfoVO>> getUserGroup(@RequestBody @Valid AtyUserGroupParamVO userGroupParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(userGroupParamVO, userGroupParamVO.getUserId()));
    }

//    /**
//     * 已绑定的用户组列表（不分页查询，暂时无用）
//     *
//     * @param userDetailVO
//     * @return DefaultCommonResult<List<AtyGroupInfoVO>>
//     */
//    @GetMapping("/groups/list")
//    public DefaultCommonResult<List<AtyGroupInfoVO>> getUserGroup(@Valid AtyUserDetailVO userDetailVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(userDetailVO.getRealm(), userDetailVO.getUserId()));
//    }

    /**
     * 绑定单个用户组
     *
     * @param atyUserGroupVO
     * @return DefaultCommonResult
     */
    @PostMapping("/groups")
    public DefaultCommonResult addUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.addBatchByUsers(atyUserGroupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑单个用户组
     * 注释：因openApi不支持DeleteMapping带RequestBody
     * @param atyUserGroupVO
     * @return DefaultCommonResult
     */
    @PostMapping("/groups/delete")
    public DefaultCommonResult deleteUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.deleteUserGroup(atyUserGroupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 用户信息-所属分组-批量绑定-用户组
     *
     * @param batchByGroupsVO
     * @return DefaultCommonResult
     */
    @PostMapping("/groups/batch")
    public DefaultCommonResult addBatchByGroups(@RequestBody @Valid AtyGroupBatchByGroupsVO batchByGroupsVO) {
        atyUserGroupService.addBatchByGroups(batchByGroupsVO);
        return DefaultCommonResult.success();
    }

    /**
     * 已绑定的角色列表
     * @param userRoleParamVO
     * @return
     */
    @GetMapping("/roles/list")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid AtyUserRoleParamVO userRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(userRoleParamVO.getRealm(), userRoleParamVO.getUserId(),userRoleParamVO.getClient_clientId()));
    }

    /**
     * 绑定单个角色
     *
     * @param userClientRole
     * @return DefaultCommonResult
     */
    @PostMapping("/roles")
    public DefaultCommonResult addUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.addBatchByUsers(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑单个角色
     * 注释：因openApi不支持DeleteMapping带RequestBody
     * @param userClientRole
     * @return DefaultCommonResult
     */
    @PostMapping("/roles/delete")
    public DefaultCommonResult deleteUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.deleteUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 用户权限-用户角色-批量绑定-角色
     *
     * @param batchByRolesVO
     * @return DefaultCommonResult
     */
    @PostMapping("/roles/batch")
    public DefaultCommonResult addBatchByRoles(@RequestBody @Valid AtyRoleBatchByRolesVO batchByRolesVO) {
        atyUserRoleService.addBatchByRoles(batchByRolesVO);
        return DefaultCommonResult.success();
    }
}
