package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 权限管理_用户与角色关联
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping()
public class AtyUserRoleController {
    private final AtyUserRoleService atyUserRoleService;

    public AtyUserRoleController(AtyUserRoleService atyUserRoleService) {
        this.atyUserRoleService = atyUserRoleService;
    }

    /**
     * 用户角色列表
     * @param realm
     * @param userId
     * @param client_clientId 唯一标识，客户端id
     * @return
     */
    @GetMapping("/users/{userId}/roles")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid @NotBlank String realm, @PathVariable String userId,@Valid @NotBlank String client_clientId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(realm, userId,client_clientId));
    }

    /**
     * 绑定
     *
     * @param userClientRole
     * @return DefaultCommonResult
     */
    @PostMapping("/users/roles")
    public DefaultCommonResult addUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.addBatchByUsers(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑
     *
     * @param userClientRole
     * @return DefaultCommonResult
     */
    @DeleteMapping("/users/roles")
    public DefaultCommonResult deleteUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.deleteUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定-角色
     *
     * @param batchByRolesVO
     * @return DefaultCommonResult
     */
    @PostMapping("/users/roles/batch")
    public DefaultCommonResult addBatchByRoles(@RequestBody @Valid AtyRoleBatchByRolesVO batchByRolesVO) {
        atyUserRoleService.addBatchByRoles(batchByRolesVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定-用户
     *
     * @param atyGroupBatchVO
     * @return DefaultCommonResult
     */
    @PostMapping("/roles/users/batch")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyUserRoleService.addBatchByUsers(atyGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
