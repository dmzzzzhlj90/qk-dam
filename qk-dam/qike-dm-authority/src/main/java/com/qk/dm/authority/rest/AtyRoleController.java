package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleUpdateVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理_客户端角色
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/client/{client_id}/role")
public class AtyRoleController {
    private final AtyRoleService atyRoleService;

    public AtyRoleController(AtyRoleService atyRoleService) {
        this.atyRoleService = atyRoleService;
    }

    /**
     * 新增角色
     * @param client_id
     * @param clientRoleVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addClientRole(@PathVariable String client_id, @RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.addClientRole(clientRoleVO.getRealm(),client_id,clientRoleVO.getRoleName(),clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 修改角色
     * @param client_id
     * @param roleName
     * @param clientRoleVO
     * @return
     */
    @PutMapping("/{roleName}")
    public DefaultCommonResult updateClientRole(@PathVariable String client_id,@PathVariable String roleName, @RequestBody @Valid AtyClientRoleUpdateVO clientRoleVO) {
        atyRoleService.updateClientRole(clientRoleVO.getRealm(),client_id,roleName,clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 删除角色
     * @param client_id
     * @param roleName
     * @param realm
     * @return
     */
    @DeleteMapping("/{roleName}")
    public DefaultCommonResult deleteClientRole(@PathVariable String client_id, @PathVariable String roleName, @Valid @NotNull String realm) {
        atyRoleService.deleteClientRole(realm, client_id, roleName);
        return DefaultCommonResult.success();
    }

    /**
     * 角色详情
     * @param client_id
     * @param roleName
     * @param realm
     * @return
     */
    @GetMapping("/{roleName}")
    public DefaultCommonResult<AtyClientRoleInfoVO> getClientRole(@PathVariable String client_id, @PathVariable String roleName, @Valid @NotNull String realm) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getClientRole(realm, client_id, roleName));
    }

    /**
     * 角色分页列表
     * @param client_id
     * @param clientRoleParamVO
     * @return
     */
    @PostMapping("/page/list")
    public DefaultCommonResult<PageResultVO<AtyClientRoleInfoVO>> getClientRoles(@PathVariable String client_id, @RequestBody AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                atyRoleService.getClientRoles(clientRoleParamVO.getRealm(),client_id,clientRoleParamVO.getSearch(), clientRoleParamVO.getPagination()));
    }

    /**
     * 角色列表
     * @param client_id
     * @param realm
     * @param search
     * @return
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getClientRoles(@PathVariable String client_id, String realm, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getUsers(realm, client_id, search));
    }
}
