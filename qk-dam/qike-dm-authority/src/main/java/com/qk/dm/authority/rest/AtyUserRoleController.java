//package com.qk.dm.authority.rest;
//
//import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
//import com.qk.dam.commons.enums.ResultCodeEnum;
//import com.qk.dam.commons.http.result.DefaultCommonResult;
//import com.qk.dm.authority.service.AtyUserRoleService;
//import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByRolesVO;
//import com.qk.dm.authority.vo.clientrole.AtyRoleBatchByUsersVO;
//import com.qk.dm.authority.vo.clientrole.AtyUserClientRoleVO;
//import com.qk.dm.authority.vo.user.AtyUserRoleParamVO;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * 权限管理_用户与角色关联
// *
// * @author shenpj
// * @date 2022/3/2 10:41
// * @since 1.0.0
// */
//@RestController
//@RequestMapping()
//public class AtyUserRoleController {
//    private final AtyUserRoleService atyUserRoleService;
//
//    public AtyUserRoleController(AtyUserRoleService atyUserRoleService) {
//        this.atyUserRoleService = atyUserRoleService;
//    }
//
//    /**
//     * 用户-用户角色列表
//     * @param userRoleParamVO
//     * @return
//     */
//    @GetMapping("/users/roles")
//    public DefaultCommonResult<List<AtyClientRoleInfoVO>> getUserClientRole(@Valid AtyUserRoleParamVO userRoleParamVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserRoleService.getUserClientRole(userRoleParamVO.getRealm(), userRoleParamVO.getUserId(),userRoleParamVO.getClient_clientId()));
//    }
//
//    /**
//     * 用户绑定单个角色
//     *
//     * @param userClientRole
//     * @return DefaultCommonResult
//     */
//    @PostMapping("/users/roles")
//    public DefaultCommonResult addUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
//        atyUserRoleService.addBatchByUsers(userClientRole);
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 用户解绑单个角色
//     *
//     * @param userClientRole
//     * @return DefaultCommonResult
//     */
//    @DeleteMapping("/users/roles")
//    public DefaultCommonResult deleteUserClientRole(@RequestBody @Valid AtyUserClientRoleVO userClientRole) {
//        atyUserRoleService.deleteUserClientRole(userClientRole);
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 用户权限-用户角色-批量绑定-角色
//     *
//     * @param batchByRolesVO
//     * @return DefaultCommonResult
//     */
//    @PostMapping("/users/roles/batch")
//    public DefaultCommonResult addBatchByRoles(@RequestBody @Valid AtyRoleBatchByRolesVO batchByRolesVO) {
//        atyUserRoleService.addBatchByRoles(batchByRolesVO);
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 角色详情-授权-批量绑定-用户
//     *
//     * @param atyGroupBatchVO
//     * @return DefaultCommonResult
//     */
//    @PostMapping("/roles/users/batch")
//    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyRoleBatchByUsersVO atyGroupBatchVO) {
//        atyUserRoleService.addBatchByUsers(atyGroupBatchVO);
//        return DefaultCommonResult.success();
//    }
//}
