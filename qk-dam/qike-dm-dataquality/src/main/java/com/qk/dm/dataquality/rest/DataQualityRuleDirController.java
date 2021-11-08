package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DataQualityRuleDirService;
import com.qk.dm.dataquality.vo.DataQualityRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DataQualityRuleDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据质量_规则模板目录
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/dir")
public class DataQualityRuleDirController {
    private final DataQualityRuleDirService dataQualityRuleDirService;

    @Autowired
    public DataQualityRuleDirController(DataQualityRuleDirService dataQualityRuleDirService) {
        this.dataQualityRuleDirService = dataQualityRuleDirService;
    }

    /**
     * 获取规则模板分类目录树
     *
     * @return DefaultCommonResult<List<DataQualityRuleDirTreeVO>>
     */
    @GetMapping("/list")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DataQualityRuleDirTreeVO>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataQualityRuleDirService.searchList());
    }

    /**
     * 新增规则模板分类目录
     *
     * @param dataQualityRuleDirVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody DataQualityRuleDirVO dataQualityRuleDirVO) {
        dataQualityRuleDirService.insert(dataQualityRuleDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则模板分类目录
     *
     * @param dataQualityRuleDirVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody DataQualityRuleDirVO dataQualityRuleDirVO) {
        dataQualityRuleDirService.update(dataQualityRuleDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 标准目录单子节点删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") Integer id) {
        dataQualityRuleDirService.delete(id);
        return DefaultCommonResult.success();
    }

    /**
     * 标准目录支持根节点关联删除子节点方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{id}")
//  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("id") Integer id) {
        dataQualityRuleDirService.deleteBulk(id);
        return DefaultCommonResult.success();
    }
}
