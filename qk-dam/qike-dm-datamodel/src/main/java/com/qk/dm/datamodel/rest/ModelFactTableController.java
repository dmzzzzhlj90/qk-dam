package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import com.qk.dm.datamodel.service.ModelFactTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事实表
 * @author wangzp
 * @date 2021/11/10 10:28
 * @since 1.0.0
 */
@RequestMapping("/fact")
@RestController
public class ModelFactTableController {

    private final ModelFactTableService modelFactTableService;

    public ModelFactTableController(ModelFactTableService modelFactTableService){
        this.modelFactTableService = modelFactTableService;
    }

    /**
     * 添加事实表
     * @param modelFactTableDTO 事实表实体
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated ModelFactTableDTO modelFactTableDTO){
        modelFactTableService.insert(modelFactTableDTO);
        return DefaultCommonResult.success();
    }
    /**
     * 修改事实表
     * @param modelFactTableDTO 事实表实体,编辑id不能为空
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@RequestBody @Validated ModelFactTableDTO modelFactTableDTO){
        modelFactTableService.update(modelFactTableDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 事实表详情
     * @param id 事实表id
     * @return DefaultCommonResult<ModelFactTableVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelFactTableVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.detail(id));
    }

    /**
     * 事实表列表
     * @param modelFactTableDTO 事实表实体
     * @return DefaultCommonResult<PageResultVO<ModelFactTableVO>>
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelFactTableVO>> list(@RequestBody ModelFactTableDTO modelFactTableDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.list(modelFactTableDTO));
    }
    /**
     * 发布事实表
     * @param idList
     * @return DefaultCommonResult
     */
    @PutMapping("/publish")
    public DefaultCommonResult publish(@RequestBody @Validated List<Long> idList) {
        modelFactTableService.publish(idList);
        return DefaultCommonResult.success();
    }

    /**
     * 下线事实表
     * @param idList
     * @return DefaultCommonResult
     */
    @PutMapping("/offline")
    public DefaultCommonResult offline(@RequestBody @Validated List<Long> idList) {
        modelFactTableService.offline(idList);
        return DefaultCommonResult.success();
    }

    /**
     * 删除事实表
     * @param ids
     * @return
     */
    @DeleteMapping("{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        modelFactTableService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 预览SQL
     * @param tableId 事实表id
     * @return DefaultCommonResult<String>
     */
    @GetMapping("/preview/sql/{tableId}")
    public DefaultCommonResult<String> previewSql(@PathVariable("tableId") Long tableId){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.previewSql(tableId));
    }

}
