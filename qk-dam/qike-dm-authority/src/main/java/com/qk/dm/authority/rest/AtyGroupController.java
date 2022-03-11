package com.qk.dm.authority.rest;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupUserParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
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

    public AtyGroupController(AtyGroupService atyUserGroupService) {
        this.atyGroupService = atyUserGroupService;
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
     * @param groupId
     * @param groupVO
     * @return DefaultCommonResult
     */
    @PutMapping("/{groupId}")
    public DefaultCommonResult updateGroup(@PathVariable String groupId, @RequestBody @Valid AtyGroupVO groupVO) {
        atyGroupService.updateGroup(groupId, groupVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除用户组
     *
     * @param groupId
     * @param realm
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{groupId}")
    public DefaultCommonResult deleteGroup(@Valid @NotBlank String realm, @PathVariable String groupId) {
        atyGroupService.deleteGroup(realm, groupId);
        return DefaultCommonResult.success();
    }

    /**
     * 用户组详情
     *
     * @param realm
     * @param groupId
     * @return DefaultCommonResult<AtyGroupInfoVO>
     */
    @GetMapping("/{groupId}")
    public DefaultCommonResult<AtyGroupInfoVO> getUser(@Valid @NotBlank String realm, @PathVariable String groupId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroup(realm, groupId));
    }

    /**
     * 用户组分页列表
     *
     * @param groupParamVO
     * @return DefaultCommonResult<PageResultVO < AtyGroupInfoVO>>
     */
    @PostMapping("/page")
    public DefaultCommonResult<PageResultVO<AtyGroupInfoVO>> getUsers(@RequestBody AtyGroupParamVO groupParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getUsers(groupParamVO));
    }

    /**
     * 用户组不分页列表
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
     * 用户组下的用户
     * @param groupUserParamVO
     * @param groupId
     * @return <PageResultVO<AtyGroupInfoVO>>
     */
    @PostMapping("/{groupId}/users")
    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> getGroupUsers(@RequestBody @Valid AtyGroupUserParamVO groupUserParamVO, @PathVariable String groupId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupUsers(groupUserParamVO, groupId));
    }

//    @GetMapping("/{groupId}/users")
//    public DefaultCommonResult<List<AtyUserInfoVO>> getUserGroupUsers(@Valid @NotBlank String realm, @PathVariable String groupId) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroupUsers(realm, groupId));
//    }
}
