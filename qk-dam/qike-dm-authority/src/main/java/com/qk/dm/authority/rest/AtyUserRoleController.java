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
     * @return
     */
    @GetMapping("/users/{userId}/roles")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid @NotBlank String realm, @PathVariable String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(realm, userId));
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
