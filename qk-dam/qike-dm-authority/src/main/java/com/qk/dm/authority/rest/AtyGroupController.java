package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupParamVO;
import com.qk.dm.authority.vo.group.AtyGroupVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理_用户组
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/group")
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
     * @param groupVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult updateGroup(@RequestBody @Valid AtyGroupVO groupVO) {
        atyGroupService.updateGroup(groupVO.getId(), groupVO);
        return DefaultCommonResult.success();
    }


    /**
     * 删除用户组
     *
     * @param groupVO
     * @return DefaultCommonResult
     */
    @DeleteMapping("")
    public DefaultCommonResult deleteGroup(@RequestBody @Valid AtyGroupVO groupVO) {
        atyGroupService.deleteGroup(groupVO.getRealm(), groupVO.getId());
        return DefaultCommonResult.success();
    }

    /**
     * 用户组详情
     * @param realm
     * @param id
     * @return DefaultCommonResult<AtyGroupInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<AtyGroupInfoVO> getUser(@Valid @NotNull String realm, @PathVariable String id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getGroup(realm, id));
    }

    /**
     * 用户组分页列表
     *
     * @param groupParamVO
     * @return DefaultCommonResult<PageResultVO < AtyGroupInfoVO>>
     */
    @PostMapping("/page/list")
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
    public DefaultCommonResult<List<AtyGroupInfoVO>> getUsers(@Valid @NotNull String realm, String search) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyGroupService.getUsers(realm, search));
    }
}
