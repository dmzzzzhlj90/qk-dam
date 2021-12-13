package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.service.RptSelectorColumnInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据采集选择器配置
 * @author wangzp
 * @date 2021/12/10 16:19
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/selector")
public class RptSelectorColumnInfoController {

    private final RptSelectorColumnInfoService rptSelectorColumnInfoService;

    public RptSelectorColumnInfoController(RptSelectorColumnInfoService rptSelectorColumnInfoService){
        this.rptSelectorColumnInfoService = rptSelectorColumnInfoService;
    }

    /**
     * 添加选择器信息
     * @param rptSelectorColumnInfoDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO){
        rptSelectorColumnInfoService.insert(rptSelectorColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改选择器信息
     * @param id
     * @param rptSelectorColumnInfoDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO) {
        rptSelectorColumnInfoService.update(id, rptSelectorColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 选择器列表
     * @param configId
     * @return DefaultCommonResult<List<RptSelectorColumnInfoVO>>
     */
    @GetMapping("/list/{configId}")
    public DefaultCommonResult<List<RptSelectorColumnInfoVO>> list(
            @PathVariable("configId") Long configId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptSelectorColumnInfoService.list(configId));
    }


    /**
     * 详情
     * @param id
     * @return DefaultCommonResult<RptConfigInfoVO>
     */
    @GetMapping("/detail/{id}")
    public DefaultCommonResult<RptSelectorColumnInfoVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptSelectorColumnInfoService.detail(id));
    }

    /**
     * 删除选择器
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        rptSelectorColumnInfoService.delete(ids);
        return DefaultCommonResult.success();
    }
}
