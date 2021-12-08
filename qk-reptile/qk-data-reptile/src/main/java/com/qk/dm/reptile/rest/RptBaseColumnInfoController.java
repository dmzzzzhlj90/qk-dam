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
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptBaseColumnInfoDTO rptBaseColumnInfoDTO){
        rptBaseColumnInfoService.insert(rptBaseColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改选择器信息
     * @param rptBaseColumnInfoDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptBaseColumnInfoDTO rptBaseColumnInfoDTO) {
        rptBaseColumnInfoService.update(id, rptBaseColumnInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量添加或修改
     * @param baseInfoId
     * @param rptBaseColumnInfoDTOList
     * @return
     */
    @PostMapping("/batch/{baseInfoId}")
    public DefaultCommonResult insert( @PathVariable("baseInfoId") Long baseInfoId, @RequestBody @Validated List<RptBaseColumnInfoDTO> rptBaseColumnInfoDTOList){
        rptBaseColumnInfoService.batchInset(baseInfoId,rptBaseColumnInfoDTOList);
        return DefaultCommonResult.success();
    }

    /**
     * 选择器列表
     * @param baseInfoId
     * @return
     */
    @GetMapping("/list/{baseInfoId}")
    public DefaultCommonResult<List<RptBaseColumnInfoVO>> list(@PathVariable("baseInfoId") Long baseInfoId){
       return DefaultCommonResult.success(ResultCodeEnum.OK,rptBaseColumnInfoService.list(baseInfoId));
    }
}
