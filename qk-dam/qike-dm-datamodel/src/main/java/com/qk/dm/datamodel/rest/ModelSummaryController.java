package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import com.qk.dm.datamodel.service.ModelSummaryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 汇总表
 * @author wangzp
 * @date 2021/11/12 16:26
 * @since 1.0.0
 */
@RequestMapping("/summary")
@RestController
public class ModelSummaryController {

    private final ModelSummaryService modelSummaryService;

    private ModelSummaryController(ModelSummaryService modelSummaryService){
        this.modelSummaryService = modelSummaryService;
    }

    /**
     * 添加汇总表
     * @param modelSummaryDTO 汇总表实体
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated ModelSummaryDTO modelSummaryDTO){
        modelSummaryService.insert(modelSummaryDTO);
        return DefaultCommonResult.success();
    }
    /**
     * 修改汇总表
     * @param modelSummaryDTO 汇总表实体 id不能为空
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@RequestBody @Validated ModelSummaryDTO modelSummaryDTO){
        modelSummaryService.update(modelSummaryDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 汇总表详情
     * @param id 汇总表id
     * @return DefaultCommonResult<ModelSummaryVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelSummaryVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelSummaryService.detail(id));
    }

    /**
     * 汇总表列表
     * @param modelSummaryDTO 汇总表实体
     * @return DefaultCommonResult<PageResultVO<ModelSummaryVO>>
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelSummaryVO>> list(@RequestBody ModelSummaryDTO modelSummaryDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelSummaryService.list(modelSummaryDTO));
    }
    /**
     * 发布汇总表
     * @param idList
     * @return DefaultCommonResult
     */
    @PutMapping("/publish")
    public DefaultCommonResult publish(@RequestBody @Validated List<Long> idList) {
        modelSummaryService.publish(idList);
        return DefaultCommonResult.success();
    }

    /**
     * 下线汇总表
     * @param idList
     * @return DefaultCommonResult
     */
    @PutMapping("/offline")
    public DefaultCommonResult offline(@RequestBody @Validated List<Long> idList) {
        modelSummaryService.offline(idList);
        return DefaultCommonResult.success();
    }

    /**
     * 删除汇总表
     * @param ids
     * @return
     */
    @DeleteMapping("{ids}")
    public DefaultCommonResult batchDelete(@PathVariable("ids") String ids){
        modelSummaryService.delete(ids);
        return DefaultCommonResult.success();
    }
    /**
     * 预览SQL
     * @param tableId 汇总表id
     * @return DefaultCommonResult<String>
     */
    @GetMapping("/preview/sql/{tableId}")
    public DefaultCommonResult<String> previewSql(@PathVariable("tableId") Long tableId){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelSummaryService.previewSql(tableId));
    }
}
