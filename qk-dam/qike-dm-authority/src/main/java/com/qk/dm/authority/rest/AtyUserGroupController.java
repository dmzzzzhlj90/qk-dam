package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyUserGroupBatchVO;
import com.qk.dm.authority.vo.group.AtyUserGroupVO;
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
@RequestMapping("/user/group")
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
    @GetMapping("")
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUserGroup(@Valid @NotNull String realm,@Valid @NotNull String userId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserGroupService.getUserGroup( realm,userId));
    }

    /**
     * 绑定用户组
     * @param atyUserGroupVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult addUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.addUserGroup(atyUserGroupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 解绑用户组
     * @param atyUserGroupVO
     * @return
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteUserGroup(@RequestBody @Valid AtyUserGroupVO atyUserGroupVO) {
        atyUserGroupService.deleteUserGroup(atyUserGroupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量绑定用户
     *
     * @param atyUserGroupBatchVO
     * @return
     */
    @PostMapping("/batch")
    public DefaultCommonResult addUserGroup(@RequestBody @Valid AtyUserGroupBatchVO atyUserGroupBatchVO) {
        atyUserGroupService.addUserGroup(atyUserGroupBatchVO);
        return DefaultCommonResult.success();
    }
}
