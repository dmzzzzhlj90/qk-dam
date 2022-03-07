package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理_用户与角色关联
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/role")
public class AtyUserRoleController {
    private final AtyUserRoleService atyUserRoleService;

    public AtyUserRoleController(AtyUserRoleService atyUserRoleService) {
        this.atyUserRoleService = atyUserRoleService;
    }

    /**
     * 查询已绑定
     *
     * @param userId
     * @param realm
     * @param client_clientId
     * @return
     */
    @GetMapping("")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid @NotNull String userId, @Valid @NotNull String realm, @Valid @NotNull String client_clientId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(realm, userId, client_clientId));
    }

    /**
     * 绑定
     *
     * @param userClientRole
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addUserClientRole(@RequestBody AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.addBatchByUsers(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑
     *
     * @param userClientRole
     * @return
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteUserClientRole(@RequestBody AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.deleteUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定-用户
     *
     * @param atyGroupBatchVO
     * @return
     */
    @PostMapping("/batch-users")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyUserRoleService.addBatchByUsers(atyGroupBatchVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定-角色
     *
     * @param batchByRolesVO
     * @return
     */
    @PostMapping("/batch-roles")
    public DefaultCommonResult addBatchByRoles(@RequestBody @Valid AtyRoleBatchByRolesVO batchByRolesVO) {
        atyUserRoleService.addBatchByRoles(batchByRolesVO);
        return DefaultCommonResult.success();
    }
}
