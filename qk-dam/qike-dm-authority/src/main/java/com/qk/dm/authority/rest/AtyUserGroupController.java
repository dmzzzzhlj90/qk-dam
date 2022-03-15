package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import com.qk.dm.authority.vo.user.AtyUserGroupParamVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 权限管理_用户与用户组关联
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping()
public class AtyUserGroupController {
    private final AtyUserGroupService atyUserGroupService;

    public AtyUserGroupController(AtyUserGroupService atyUserGroupService) {
        this.atyUserGroupService = atyUserGroupService;
    }

    /**
     * 用户-已绑定的用户组列表
     *
     * @param userId
     * @param userGroupParamVO
     * @return DefaultCommonResult<PageResultVO < AtyGroupInfoVO>>
     */
    @PostMapping("/users/{userId}/groups")
    public DefaultCommonResult<PageResultVO<AtyGroupInfoVO>> getUserGroup(@RequestBody @Valid AtyUserGroupParamVO userGroupParamVO, @PathVariable String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(userGroupParamVO, userId));
    }

//    /**
//     * 用户-已绑定用户组列表（不分页查询，暂时无用）
//     *
//     * @param userId
//     * @param realm
//     * @return DefaultCommonResult<List<AtyGroupInfoVO>>
//     */
//    @GetMapping("/users/{userId}/groups")
//    public DefaultCommonResult<List<AtyGroupInfoVO>> getUserGroup(@Valid @NotBlank String realm, @PathVariable String userId) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(realm, userId));
//    }

    /**
     * 用户绑定单个用户组
     *
     * @param atyUserGroupVO
     * @return DefaultCommonResult
     */
    @PostMapping("/users/groups")
    public DefaultCommonResult addUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.addBatchByUsers(atyUserGroupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 用户解绑单个用户组
     *
     * @param atyUserGroupVO
     * @return DefaultCommonResult
     */
    @DeleteMapping("/users/groups")
    public DefaultCommonResult deleteUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.deleteUserGroup(atyUserGroupVO);
        return DefaultCommonResult.success();
    }


    /**
     * 用户详情-所属分组-批量绑定-用户组
     *
     * @param batchByGroupsVO
     * @return DefaultCommonResult
     */
    @PostMapping("/users/groups/batch")
    public DefaultCommonResult addBatchByGroups(@RequestBody @Valid AtyGroupBatchByGroupsVO batchByGroupsVO) {
        atyUserGroupService.addBatchByGroups(batchByGroupsVO);
        return DefaultCommonResult.success();
    }

    /**
     * 用户组详情-已授权用户-批量绑定-用户
     *
     * @param atyGroupBatchByUsersVO
     * @return DefaultCommonResult
     */
    @PostMapping("/groups/users/batch")
    public DefaultCommonResult addBatchByUsers(@RequestBody @Valid AtyGroupBatchByUsersVO atyGroupBatchByUsersVO) {
        atyUserGroupService.addBatchByUsers(atyGroupBatchByUsersVO);
        return DefaultCommonResult.success();
    }
}
