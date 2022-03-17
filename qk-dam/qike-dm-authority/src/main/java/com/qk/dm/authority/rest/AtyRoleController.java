package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleUserParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理_客户端角色
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/roles")
public class AtyRoleController {
    private final AtyRoleService atyRoleService;
    private final AtyUserRoleService atyUserRoleService;

    public AtyRoleController(AtyRoleService atyRoleService, AtyUserRoleService atyUserRoleService) {
        this.atyRoleService = atyRoleService;
        this.atyUserRoleService = atyUserRoleService;
    }

    /**
     * 新增角色
     *
     * @param clientRoleVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult addClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.addClientRole(clientRoleVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改角色
     *
     * @param clientRoleVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult updateClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.updateClientRole(clientRoleVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除角色
     *
     * @param clientRoleVO
     * @return DefaultCommonResult
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.deleteClientRole(clientRoleVO);
        return DefaultCommonResult.success();
    }

    /**
     * 角色详情
     *
     * @param clientRoleVO
     * @return DefaultCommonResult<AtyClientRoleInfoVO>
     */
    @GetMapping("")
    public DefaultCommonResult<AtyClientRoleInfoVO> getClientRole(@Valid AtyClientRoleVO clientRoleVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getClientRole(clientRoleVO));
    }

    /**
     * 角色列表
     *
     * @param clientRoleParamVO
     * @return DefaultCommonResult<PageResultVO<AtyClientRoleInfoVO>>
     */
    @PostMapping("/page")
    public DefaultCommonResult<PageResultVO<AtyClientRoleInfoVO>> getClientRolesPage(@RequestBody @Valid AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,atyRoleService.getClientRoles(clientRoleParamVO));
    }

    /**
     * 用户-用户角色-添加-角色列表
     *
     * @param clientRoleParamVO
     * @return DefaultCommonResult<List<AtyClientRoleInfoVO>>
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getClientRoles(@Valid AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getUsers(clientRoleParamVO));
    }

    /**
     * 已授权的用户列表
     * @param clientRoleVO
     * @return DefaultCommonResult<PageResultVO<AtyUserInfoVO>>
     */
    @PostMapping("/users")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getUserClientRoleUsersPage(@RequestBody @Valid AtyClientRoleUserParamVO clientRoleVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getRoleUsersPage(clientRoleVO));
    }

//    /**
//     * 角色-已授权用户列表（不分页查询，暂时无用）
//     * @param clientRoleVO
//     * @return
//     */
//    @GetMapping("/users")
//    public DefaultCommonResult<List<AtyUserInfoVO>> getUserClientRoleUsers(@Valid AtyClientRoleUserParamVO clientRoleVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getRoleUsers(clientRoleVO));
//    }

    /**
     * 角色详情-添加授权-批量绑定-用户
     *
     * @param atyGroupBatchVO
     * @return DefaultCommonResult
     */
    @PostMapping("/users/batch")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyUserRoleService.addBatchByUsers(atyGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
