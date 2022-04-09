package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 权限管理_用户组
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/groups")
public class AtyGroupController {
    private final AtyGroupService atyGroupService;
    private final AtyUserGroupService atyUserGroupService;

    public AtyGroupController(AtyGroupService atyUserGroupService, AtyUserGroupService atyUserGroupService1) {
        this.atyGroupService = atyUserGroupService;
        this.atyUserGroupService = atyUserGroupService1;
    }

    /**
     * 新增用户组
     *
     * @param groupVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult addGroup(@RequestBody @Valid AtyGroupVO groupVO) {
        atyGroupService.addGroup(groupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改用户组
     *
     * @param groupVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult updateGroup(@RequestBody @Valid AtyGroupVO groupVO) {
        atyGroupService.updateGroup(groupVO.getGroupId(), groupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户组
     * 注释：因openApi不支持DeleteMapping带RequestBody
     * @param groupDetailVO
     * @return DefaultCommonResult
     */
    @PostMapping("/delete")
    public DefaultCommonResult deleteGroup(@RequestBody @Valid AtyGroupDetailVO groupDetailVO) {
        atyGroupService.deleteGroup(groupDetailVO.getRealm(), groupDetailVO.getGroupId());
        return DefaultCommonResult.success();
    }

    /**
     * 用户组详情
     *
     * @param groupDetailVO
     * @return DefaultCommonResult<AtyGroupInfoVO>
     */
    @GetMapping("")
    public DefaultCommonResult<AtyGroupInfoVO> getUser(@Valid AtyGroupDetailVO groupDetailVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroup(groupDetailVO.getRealm(), groupDetailVO.getGroupId()));
    }

    /**
     * 用户组列表
     *
     * @param groupParamVO
     * @return DefaultCommonResult<PageResultVO < AtyGroupInfoVO>>
     */
    @PostMapping("/page")
    public DefaultCommonResult<PageResultVO<AtyGroupInfoVO>> getUsers(@RequestBody AtyGroupParamVO groupParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupPage(groupParamVO));
    }

    /**
     * 用户组列表-不分页查询所有
     * @param realm
     * @param search
     * @return
     */
    @GetMapping("/list")
    @Validated
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUsersGroups(@NotBlank @RequestParam("realm") String realm, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupList(realm, search));
    }

    /**
     * 用户组详情-已绑定的用户列表
     *
     * @param groupUserParamVO
     * @return <PageResultVO<AtyGroupInfoVO>>
     */
    @PostMapping("/users")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getGroupUsers(@RequestBody @Valid AtyGroupUserParamVO groupUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getGroupUsers(groupUserParamVO, groupUserParamVO.getGroupId()));
    }

    /**
     * 用户组详情-添加用户-分配的用户列表
     * @param groupDetailVO
     * @return
     */
    @GetMapping("/users")
    public DefaultCommonResult<List<AtyUserInfoVO>> getGroupUsersList(@Valid AtyGroupDetailVO groupDetailVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getGroupUsers(groupDetailVO.getRealm(), groupDetailVO.getGroupId()));
    }

    /**
     * 用户组详情-添加用户-排除已授权的用户列表
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/users/filtro")
    public DefaultCommonResult<List<AtyUserInfoVO>> getUsers(@Valid AtyGroupUserFiltroVO userFiltroVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,atyUserGroupService.getUserFiltro(userFiltroVO));
    }

    /**
     * 用户组详情-添加用户-批量绑定
     *
     * @param atyGroupBatchByUsersVO
     * @return DefaultCommonResult
     */
    @PostMapping("/users/batch")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyGroupBatchByUsersVO atyGroupBatchByUsersVO) {
        atyUserGroupService.addBatchByUsers(atyGroupBatchByUsersVO);
        return DefaultCommonResult.success();
    }
}
