package com.qk.dm.authority.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupCreateVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 权限管理_用户
 *
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/group")
public class AtyGroupController {
    private final AtyGroupService atyUserGroupService;

    public AtyGroupController(AtyGroupService atyUserGroupService) {
        this.atyUserGroupService = atyUserGroupService;
    }


    @PostMapping("")
    public DefaultCommonResult addGroup(@RequestBody @Valid AtyGroupCreateVO groupVO) {
        atyUserGroupService.addGroup(groupVO);
        return DefaultCommonResult.success();
    }


    @PutMapping("/{id}")
    public DefaultCommonResult updateGroup(@PathVariable("id") String groupId, @RequestBody @Valid AtyGroupCreateVO groupVO) {
        atyUserGroupService.updateGroup(groupId, groupVO);
        return DefaultCommonResult.success();
    }


    @DeleteMapping("/{id}")
    public DefaultCommonResult deleteGroup(@PathVariable("id") String groupId, String realm) {
        atyUserGroupService.deleteGroup(groupId,realm);
        return DefaultCommonResult.success();
    }

//    /**
//     * 用户详情
//     * @param realm
//     * @param userId
//     * @return
//     */
//    @GetMapping("/{id}")
//    public DefaultCommonResult<UserInfoVO> getUser(String realm, @PathVariable("id") String userId) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUser(realm, userId));
//    }
//
//    /**
//     * 用户列表
//     * @param userParamVO
//     * @return
//     */
//    @PostMapping("/list")
//    public DefaultCommonResult<PageResultVO<UserInfoVO>> getUsers(@RequestBody UserParamVO userParamVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, atyUserService.getUsers(userParamVO));
//    }
}
