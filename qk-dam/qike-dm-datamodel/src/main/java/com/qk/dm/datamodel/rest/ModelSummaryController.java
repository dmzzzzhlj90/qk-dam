package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.dto.ModelSummaryInfoDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import com.qk.dm.datamodel.service.ModelSummaryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/summary")
@RestController
public class ModelSummaryController {

    private final ModelSummaryService modelSummaryService;

    private ModelSummaryController(ModelSummaryService modelSummaryService){
        this.modelSummaryService = modelSummaryService;
    }

    /**
     * 添加汇总表
     * @param modelSummaryInfoDTO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated ModelSummaryInfoDTO modelSummaryInfoDTO){
        modelSummaryService.insert(modelSummaryInfoDTO);
        return DefaultCommonResult.success();
    }
    /**
     * 修改汇总表
     * @param id
     * @param modelSummaryInfoDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Validated ModelSummaryInfoDTO modelSummaryInfoDTO){
        modelSummaryService.update(id,modelSummaryInfoDTO);
        return  DefaultCommonResult.success();
    }

    /**
     * 汇总表详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<ModelSummaryVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelSummaryService.detail(id));
    }

    /**
     * 汇总表列表
     * @param modelSummaryDTO
     * @return
     */
    @GetMapping("")
    public DefaultCommonResult<PageResultVO<ModelSummaryVO>> list(@RequestBody ModelSummaryDTO modelSummaryDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,modelSummaryService.list(modelSummaryDTO));
    }
    /**
     * 发布事实表
     * @param ids
     * @return
     */
    @PutMapping("/publish/{ids}")
    public DefaultCommonResult publish(@PathVariable("ids") String ids) {
        modelSummaryService.publish(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 下线事实表
     * @param ids
     * @return DefaultCommonResult
     */
    @PutMapping("/offline/{ids}")
    public DefaultCommonResult offline(@PathVariable("ids") String ids) {
        modelSummaryService.offline(ids);
        return DefaultCommonResult.success();
    }
}
