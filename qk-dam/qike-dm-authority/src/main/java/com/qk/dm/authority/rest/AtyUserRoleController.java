package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
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
     * 查询用户已绑定角色
     *
     * @param userId
     * @param realm
     * @param client_clientId
     * @return
     */
    @GetMapping("/users/{userId}/roles")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid @NotBlank String realm, @PathVariable String userId, @Valid @NotBlank String client_clientId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(realm, userId, client_clientId));
    }

    /**
     * 绑定
     *
     * @param userClientRole
     * @return
     */
    @PostMapping("/users/roles")
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
    @DeleteMapping("/users/roles")
    public DefaultCommonResult deleteUserClientRole(@RequestBody AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.deleteUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定-角色
     *
     * @param batchByRolesVO
     * @return
     */
    @PostMapping("/users/roles/batch")
    public DefaultCommonResult addBatchByRoles(@RequestBody @Valid AtyRoleBatchByRolesVO batchByRolesVO) {
        atyUserRoleService.addBatchByRoles(batchByRolesVO);
        return DefaultCommonResult.success();
    }


    /**
     * 查询角色下所有用户
     *
     * @param realm
     * @param roleName
     * @param client_id
     * @return
     */
    @GetMapping("/roles/users")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUserClientRoleUsers(@Valid @NotBlank String realm, @Valid @NotBlank String client_id, @Valid @NotBlank String roleName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRoleUsers(realm, client_id, roleName));
    }

    /**
     * 批量绑定-用户
     *
     * @param atyGroupBatchVO
     * @return
     */
    @PostMapping("/roles/users/batch")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyRoleBatchByUsersVO atyGroupBatchVO) {
        atyUserRoleService.addBatchByUsers(atyGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
