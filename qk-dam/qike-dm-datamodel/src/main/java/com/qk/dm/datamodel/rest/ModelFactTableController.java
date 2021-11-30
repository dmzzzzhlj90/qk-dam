package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactInfoDTO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import com.qk.dm.datamodel.service.ModelFactTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 数据模型物理表
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
     * @param modelFactInfoDTO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated ModelFactInfoDTO modelFactInfoDTO){
        modelFactTableService.insert(modelFactInfoDTO);
        return DefaultCommonResult.success();
    }
    /**
     * 修改事实表
     * @param id
     * @param modelFactInfoDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Validated ModelFactInfoDTO modelFactInfoDTO){
        modelFactTableService.update(id,modelFactInfoDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 事实表详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelFactTableVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.detail(id));
    }

    /**
     * 事实表列表
     * @param modelFactTableDTO
     * @return
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelFactTableVO>> list(@RequestBody ModelFactTableDTO modelFactTableDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.list(modelFactTableDTO));
    }
    /**
     * 发布事实表
     * @param ids
     * @return
     */
    @PutMapping("/publish/{ids}")
    public DefaultCommonResult publish(@PathVariable("ids") String ids) {
        modelFactTableService.publish(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 下线事实表
     * @param ids
     * @return DefaultCommonResult
     */
    @PutMapping("/offline/{ids}")
    public DefaultCommonResult offline(@PathVariable("ids") String ids) {
        modelFactTableService.offline(ids);
        return DefaultCommonResult.success();
    }
    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    @GetMapping("/preview/sql/{tableId}")
    public DefaultCommonResult<String> previewSql(@PathVariable("tableId") Long tableId){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelFactTableService.previewSql(tableId));
    }

}
