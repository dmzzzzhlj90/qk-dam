package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
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
     *
     * @param client_id 客户端ID
     * @param clientRoleVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addClientRole(@PathVariable String client_id, @RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.addClientRole(clientRoleVO.getRealm(), client_id, clientRoleVO.getRoleName(), clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 修改角色
     *
     * @param client_id 客户端ID
     * @param clientRoleVO
     * @return
     */
    @PutMapping("")
    public DefaultCommonResult updateClientRole(@PathVariable String client_id, @RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.updateClientRole(clientRoleVO.getRealm(), client_id, clientRoleVO.getRoleName(), clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 删除角色
     *
     * @param client_id 客户端ID
     * @param clientRoleVO
     * @return
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteClientRole(@PathVariable String client_id, @RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.deleteClientRole(clientRoleVO.getRealm(), client_id, clientRoleVO.getRoleName());
        return DefaultCommonResult.success();
    }

    /**
     * 角色详情
     *
     * @param client_id 客户端ID
     * @param clientRoleVO
     * @return
     */
    @GetMapping("")
    public DefaultCommonResult<AtyClientRoleInfoVO> getClientRole(@PathVariable String client_id, @Valid AtyClientRoleVO clientRoleVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getClientRole(clientRoleVO.getRealm(), client_id, clientRoleVO.getRoleName()));
    }

    /**
     * 角色分页列表
     *
     * @param client_id 客户端ID
     * @param clientRoleParamVO
     * @return
     */
    @PostMapping("/page/list")
    public DefaultCommonResult<PageResultVO<AtyClientRoleInfoVO>> getClientRoles(@PathVariable String client_id, @RequestBody AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                atyRoleService.getClientRoles(clientRoleParamVO.getRealm(), client_id, clientRoleParamVO.getSearch(), clientRoleParamVO.getPagination()));
    }

    /**
     * 角色列表
     *
     * @param client_id 客户端ID
     * @param realm
     * @param search
     * @return
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getClientRoles(@Valid @NotNull String realm, @PathVariable String client_id, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getUsers(realm, client_id, search));
    }
}
