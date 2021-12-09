package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.dto.RptBaseColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseColumnInfoVO;
import com.qk.dm.reptile.service.RptBaseColumnInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 爬虫数据采集选择器
 * @author wangzp
 * @date 2021/12/8 17:24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/baseColumn")
public class RptBaseColumnInfoController {
    private final RptBaseColumnInfoService rptBaseColumnInfoService;

    public RptBaseColumnInfoController(RptBaseColumnInfoService rptBaseColumnInfoService){
        this.rptBaseColumnInfoService = rptBaseColumnInfoService;
    }

    /**
     * 添加选择器信息
     * @param rptBaseColumnInfoDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptBaseColumnInfoDTO rptBaseColumnInfoDTO){
        rptBaseColumnInfoService.insert(rptBaseColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改选择器信息
     * @param rptBaseColumnInfoDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptBaseColumnInfoDTO rptBaseColumnInfoDTO) {
        rptBaseColumnInfoService.update(id, rptBaseColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量添加或修改
     * @param baseInfoId 基础信息id
     * @param rptBaseColumnInfoDTOList
     * @return DefaultCommonResult
     */
    @PostMapping("/batch/{baseInfoId}")
    public DefaultCommonResult insert( @PathVariable("baseInfoId") Long baseInfoId, @RequestBody @Validated List<RptBaseColumnInfoDTO> rptBaseColumnInfoDTOList){
        rptBaseColumnInfoService.batchInset(baseInfoId,rptBaseColumnInfoDTOList);
        return DefaultCommonResult.success();
    }

    /**
     * 获取选择器详情
     * @param id
     * @return DefaultCommonResult<RptBaseColumnInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<RptBaseColumnInfoVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,rptBaseColumnInfoService.detail(id));
    }

    /**
     * 删除选择器
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
    public DefaultCommonResult delete(@PathVariable("id") Long id){
        rptBaseColumnInfoService.delete(id);
        return DefaultCommonResult.success();
    }

    /**
     * 选择器列表
     * @param baseInfoId
     * @return DefaultCommonResult<List<RptBaseColumnInfoVO>>
     */
    @GetMapping("/list/{baseInfoId}")
    public DefaultCommonResult<List<RptBaseColumnInfoVO>> list(@PathVariable("baseInfoId") Long baseInfoId){
       return DefaultCommonResult.success(ResultCodeEnum.OK,rptBaseColumnInfoService.list(baseInfoId));
    }


}
