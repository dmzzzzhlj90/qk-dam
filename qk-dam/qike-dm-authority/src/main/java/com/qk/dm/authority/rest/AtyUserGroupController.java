package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchByGroupsVO;
import com.qk.dm.authority.vo.group.AtyGroupBatchByUsersVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
     * 查询用户已绑定用户组
     *
     * @param userId
     * @param realm
     * @return DefaultCommonResult<List<AtyGroupInfoVO>>
     */
    @GetMapping("/users/{userId}/groups")
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUserGroup(@Valid @NotBlank String realm, @PathVariable String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(realm, userId));
    }

    /**
     * 绑定
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
     * 解绑
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
     * 批量绑定-用户组
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
     * 批量绑定-用户
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
