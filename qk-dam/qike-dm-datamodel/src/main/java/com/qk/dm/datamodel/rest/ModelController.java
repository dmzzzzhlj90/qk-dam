package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datamodel.params.dto.ModelDTO;
import com.qk.dm.datamodel.params.vo.ModelVO;
import com.qk.dm.datamodel.service.ModelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据模型（物理模型）
 * @author wangzp
 * @date 2021/11/01 15:02
 * @since 1.0.0
 */
@RequestMapping("/model")
@RestController
public class ModelController {

    public final ModelService modelService;

    public ModelController(ModelService modelService){
        this.modelService = modelService;
    }

    /**
     * 新增—关系建模层级
     * @param modelDTO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated ModelDTO modelDTO){
        modelService.insert(modelDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 获取-关系建模层级
     * @return
     */
    @GetMapping("")
    public DefaultCommonResult<List<ModelVO>> getList(){
       return DefaultCommonResult.success(ResultCodeEnum.OK, modelService.list());
    }

}
