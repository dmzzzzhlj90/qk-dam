package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据服务_API目录
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/dir")
public class DasApiDirController {
    private final DasApiDirService dasApiDirService;

    @Autowired
    public DasApiDirController(DasApiDirService dasApiDirService) {
        this.dasApiDirService = dasApiDirService;
    }

    /**
     * 获取API目录树
     *
     * @return DefaultCommonResult<List < DaasApiTreeVO>>
     */
    @GetMapping("/list")
//  @Auth(bizType = BizResource.DAS_API_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DasApiDirTreeVO>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiDirService.searchList());
    }

    /**
     * 新增API目录
     *
     * @param dasApiDirVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiDirVO dasApiDirVO) {
        dasApiDirService.insert(dasApiDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API分类目录
     *
     * @param dasApiDirVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
//  @Auth(bizType = BizResource.DAS_API_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiDirVO dasApiDirVO) {
        dasApiDirService.update(dasApiDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * API分类目录单子节点删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
//  @Auth(bizType = BizResource.DAS_API_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") String id) {
        dasApiDirService.delete(id);
        return DefaultCommonResult.success();
    }

    /**
     * API分类目录支持根节点关联删除子节点方式
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{id}")
//  @Auth(bizType = BizResource.DAS_API_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("id") String ids) {
        dasApiDirService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }
}
