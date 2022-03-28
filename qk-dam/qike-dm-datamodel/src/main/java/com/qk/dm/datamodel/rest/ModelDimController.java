package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimQueryDTO;
import com.qk.dm.datamodel.params.vo.ModelDimDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import com.qk.dm.datamodel.service.ModelDimService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 维度
 * @author wangzp
 * @date 2021/11/10 15:21
 * @since 1.0.0
 */
@RequestMapping("/dim")
@RestController
public class ModelDimController {

    private final ModelDimService modelDimService;

    public ModelDimController(ModelDimService modelDimService){
        this.modelDimService = modelDimService;
    }

    /**
     * 添加维度信息
     * @param modelDimDTO 维度实体
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated ModelDimDTO modelDimDTO){
        modelDimService.insert(modelDimDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改维度
     * @param modelDimDTO 维度参数实体，id不能为空
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult update(@RequestBody @Validated ModelDimDTO modelDimDTO){
        modelDimService.update(modelDimDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 维度详情
     * @param id 维度id
     * @return DefaultCommonResult<ModelDimDetailVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelDimDetailVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelDimService.detail(id));
    }

    /**
     * 维度列表
     * @param modelDimQueryDTO 维度查询参数实体
     * @return DefaultCommonResult<PageResultVO<ModelDimVO>>
     */
    @PostMapping(value = "/list")
    public DefaultCommonResult<PageResultVO<ModelDimVO>> list(@RequestBody @Validated ModelDimQueryDTO modelDimQueryDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelDimService.list(modelDimQueryDTO));
    }

    /**
     * 发布维度
     * @param ids 如果多个，使用英文逗号分割
     * @return DefaultCommonResult
     */
    @PutMapping("/publish/{ids}")
    public DefaultCommonResult publish(@PathVariable("ids") String ids) {
        modelDimService.publish(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 下线维度
     * @param ids 如果多个，使用英文逗号分割
     * @return DefaultCommonResult
     */
    @PutMapping("/offline/{ids}")
    public DefaultCommonResult offline(@PathVariable("ids") String ids) {
        modelDimService.offline(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 删除维度
     * @param ids 维度id,如果多个，使用英文逗号分割
     * @return DefaultCommonResult
     */
    @DeleteMapping("{ids}")
    public DefaultCommonResult batchDelete(@PathVariable("ids") String ids){
        modelDimService.delete(ids);
        return DefaultCommonResult.success();
    }

}
