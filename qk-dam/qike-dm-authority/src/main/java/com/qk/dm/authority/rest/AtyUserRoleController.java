package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleBatchVO;
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


    @GetMapping("")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid @NotNull String userId, @Valid @NotNull String realm, @Valid @NotNull String client_clientId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(realm, userId, client_clientId));
    }

    @PostMapping("")
    public DefaultCommonResult addUserClientRole(@RequestBody AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.addUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("")
    public DefaultCommonResult deleteUserClientRole(@RequestBody AtyUserClientRoleVO userClientRole) {
        atyUserRoleService.deleteUserClientRole(userClientRole);
        return DefaultCommonResult.success();
    }

    @PostMapping("/batch")
    public DefaultCommonResult addUserClientRole(@RequestBody @Valid AtyUserClientRoleBatchVO atyGroupBatchVO) {
        atyUserRoleService.addUserClientRole(atyGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
