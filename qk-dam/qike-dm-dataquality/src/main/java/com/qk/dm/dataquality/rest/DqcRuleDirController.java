package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcRuleDirService;
import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/dir")
public class DqcRuleDirController {
    private final DqcRuleDirService dqcRuleDirService;

    @Autowired
    public DqcRuleDirController(DqcRuleDirService dqcRuleDirService) {
        this.dqcRuleDirService = dqcRuleDirService;
    }

    /**
     * 获取规则模板分类目录树
     *
     * @return DefaultCommonResult<List < DataQualityRuleDirTreeVO>>
     */
    @GetMapping("/list")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DqcRuleDirTreeVO>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleDirService.searchList());
    }

    /**
     * 新增规则模板分类目录
     *
     * @param dqcRuleDirVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody DqcRuleDirVO dqcRuleDirVO) {
        dqcRuleDirService.insert(dqcRuleDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则模板分类目录
     *
     * @param dqcRuleDirVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody DqcRuleDirVO dqcRuleDirVO) {
        dqcRuleDirService.update(dqcRuleDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 规则分类目录单子节点删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") String id) {
        dqcRuleDirService.delete(Long.parseLong(id));
        return DefaultCommonResult.success();
    }

    /**
     * 规则分类目录批量删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{id}")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("id") String id) {
        dqcRuleDirService.deleteBulk(Long.parseLong(id));
        return DefaultCommonResult.success();
    }
}
