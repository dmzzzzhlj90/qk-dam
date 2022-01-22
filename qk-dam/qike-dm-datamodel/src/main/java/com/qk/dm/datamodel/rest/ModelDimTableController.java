package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableQueryDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import com.qk.dm.datamodel.service.ModelDimTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 维度表
 * @author wangzp
 * @date 2021/11/17 15:13
 * @since 1.0.0
 */
@RequestMapping("/dim/table")
@RestController
public class ModelDimTableController {

    private final ModelDimTableService modelDimTableService;

    public ModelDimTableController(ModelDimTableService modelDimTableService){
        this.modelDimTableService = modelDimTableService;
    }



    /**
     * 修改维度表
     * @param id 维度表id
     * @param modelDimTableDTO 维度表实体
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Validated ModelDimTableDTO modelDimTableDTO){
        modelDimTableService.update(id,modelDimTableDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 维度表详情
     * @param id 维度表id
     * @return DefaultCommonResult<ModelDimTableDetailVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelDimTableDetailVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelDimTableService.detail(id));
    }

    /**
     * 维度表列表
     * @param modelDimTableQueryDTO 维度表列表查询实体
     * @return DefaultCommonResult<PageResultVO<ModelDimTableVO>>
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelDimTableVO>> list(@RequestBody @Validated ModelDimTableQueryDTO modelDimTableQueryDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelDimTableService.list(modelDimTableQueryDTO));
    }

    /**
     * 预览SQL
     * @param tableId 维度表id
     * @return DefaultCommonResult<String>
     */
    @GetMapping("/preview/sql/{tableId}")
    public DefaultCommonResult<String> previewSql(@PathVariable("tableId") Long tableId){
       return DefaultCommonResult.success(ResultCodeEnum.OK,modelDimTableService.previewSql(tableId));
    }

}
