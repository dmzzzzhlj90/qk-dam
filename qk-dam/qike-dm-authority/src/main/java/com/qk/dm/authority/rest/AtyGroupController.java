package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.*;
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
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getUsers(groupParamVO));
    }

    /**
     * 用户信息-所属分组-添加-用户组列表
     *
     * @param realm
     * @param search
     * @return DefaultCommonResult<List < AtyGroupInfoVO>>
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUsers(@Valid @NotBlank String realm, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getUsers(realm, search));
    }

    /**
     * 已绑定的用户列表
     *
     * @param groupUserParamVO
     * @return <PageResultVO<AtyGroupInfoVO>>
     */
    @PostMapping("/users")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getGroupUsers(@RequestBody @Valid AtyGroupUserParamVO groupUserParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupUsers(groupUserParamVO, groupUserParamVO.getGroupId()));
    }

//    /**
//     * 已绑定的用户列表（不分页查询，暂时无用）
//     * @param groupDetailVO
//     * @return
//     */
//    @GetMapping("/users")
//    public DefaultCommonResult<List<AtyUserInfoVO>> getUserGroupUsers(@Valid AtyGroupDetailVO groupDetailVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupUsers(groupDetailVO.getRealm(), groupDetailVO.getGroupId()));
//    }

    /**
     * 用户组-已授权用户-批量绑定-用户
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
