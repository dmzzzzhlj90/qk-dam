package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupBatchVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
     * 查询已绑定用户组
     *
     * @param userId
     * @param realm
     * @return
     */
    @GetMapping("/user/{userId}/group")
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUserGroup(@PathVariable String userId, @Valid @NotNull String realm) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup(userId, realm));
    }

    /**
     * 绑定用户组
     *
     * @param userId
     * @param groupId
     * @param realm
     * @return
     */
    @PostMapping("/user/{userId}/group/{groupId}")
    public DefaultCommonResult addUserGroup(@PathVariable String userId, @PathVariable String groupId, @Valid @NotNull String realm) {
        atyUserGroupService.addUserGroup(userId, groupId, realm);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑用户组
     *
     * @param userId
     * @param groupId
     * @param realm
     * @return
     */
    @DeleteMapping("/user/{userId}/group/{groupId}")
    public DefaultCommonResult deleteUserGroup(@PathVariable String userId, @PathVariable String groupId, @Valid @NotNull String realm) {
        atyUserGroupService.deleteUserGroup(userId, groupId, realm);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定用户
     *
     * @param groupId
     * @param atyGroupBatchVO
     * @return
     */
    @PostMapping("/user/group/{groupId}")
    public DefaultCommonResult addUserGroup(@PathVariable String groupId, @RequestBody @Valid AtyGroupBatchVO atyGroupBatchVO) {
        atyUserGroupService.addUserGroup(groupId, atyGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
