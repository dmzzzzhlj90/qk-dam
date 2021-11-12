package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelPhysicalTableDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import com.qk.dm.datamodel.service.ModelPhysicalTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据模型物理表
 * @author wangzp
 * @date 2021/11/4 14:38
 * @since 1.0.0
 */
@RestController
@RequestMapping("/physical")
public class ModelPhysicalTableController {
    private final ModelPhysicalTableService modelPhysicalTableService;

    public ModelPhysicalTableController(ModelPhysicalTableService modelPhysicalTableService){
        this.modelPhysicalTableService = modelPhysicalTableService;
    }

    /**
     * 添加物理表
     * @param modelPhysicalTableDTO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated ModelPhysicalTableDTO modelPhysicalTableDTO){
        modelPhysicalTableService.insert(modelPhysicalTableDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改物理表信息
     * @param id
     * @param modelPhysicalTableDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@PathVariable("id") Long id,@RequestBody @Validated ModelPhysicalTableDTO modelPhysicalTableDTO){
        modelPhysicalTableService.update(id,modelPhysicalTableDTO);
        return  DefaultCommonResult.success();
    }

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        modelPhysicalTableService.delete(ids);
        return  DefaultCommonResult.success();
    }

    /**
     * 获取物理表详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelPhysicalTableVO> detail(@PathVariable("id") Long id){

       return DefaultCommonResult.success(ResultCodeEnum.OK,modelPhysicalTableService.detail(id));
    }

    /**
     * 获取物理表列表
     * @param modelPhysicalTableDTO
     * @return
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelPhysicalTableVO>> list(@RequestBody ModelPhysicalTableDTO modelPhysicalTableDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelPhysicalTableService.listPage(modelPhysicalTableDTO));
    }
}
