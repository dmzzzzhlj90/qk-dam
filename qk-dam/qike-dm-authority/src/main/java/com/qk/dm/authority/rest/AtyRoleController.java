package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyRoleService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleParamVO;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
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

    public AtyRoleController(AtyRoleService atyRoleService) {
        this.atyRoleService = atyRoleService;
    }

    /**
     * 新增角色
     *
     * @param clientRoleVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.addClientRole(clientRoleVO.getRealm(), clientRoleVO.getRoleName(), clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 修改角色
     *
     * @param clientRoleVO
     * @return
     */
    @PutMapping("")
    public DefaultCommonResult updateClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.updateClientRole(clientRoleVO.getRealm(), clientRoleVO.getRoleName(), clientRoleVO.getDescription());
        return DefaultCommonResult.success();
    }

    /**
     * 删除角色
     *
     * @param clientRoleVO
     * @return
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteClientRole(@RequestBody @Valid AtyClientRoleVO clientRoleVO) {
        atyRoleService.deleteClientRole(clientRoleVO.getRealm(), clientRoleVO.getRoleName());
        return DefaultCommonResult.success();
    }

    /**
     * 角色详情
     *
     * @param clientRoleVO
     * @return
     */
    @GetMapping("")
    public DefaultCommonResult<AtyClientRoleInfoVO> getClientRole(@Valid AtyClientRoleVO clientRoleVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getClientRole(clientRoleVO.getRealm(), clientRoleVO.getRoleName()));
    }

    /**
     * 角色分页列表
     *
     * @param clientRoleParamVO
     * @return
     */
    @PostMapping("/page")
    public DefaultCommonResult<PageResultVO<AtyClientRoleInfoVO>> getClientRolesPage(@RequestBody @Valid AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                atyRoleService.getClientRoles(clientRoleParamVO.getRealm(), clientRoleParamVO.getSearch(), clientRoleParamVO.getPagination()));
    }

    /**
     * 角色列表
     *
     * @param clientRoleParamVO
     * @return
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getClientRoles(@Valid AtyClientRoleParamVO clientRoleParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getUsers(clientRoleParamVO.getRealm(), clientRoleParamVO.getSearch()));
    }

    /**
     * 角色下的用户
     * @param clientRoleVO
     * @return
     */
    @GetMapping("/users")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUserClientRoleUsers(@Valid AtyClientRoleVO clientRoleVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRoleService.getRoleUsers(clientRoleVO.getRealm(), clientRoleVO.getRoleName()));
    }
}
