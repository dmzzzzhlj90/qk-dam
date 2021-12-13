package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.service.RptConfigInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据采集配置信息
 * @author wangzp
 * @date 2021/12/10 13:47
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class RptConfigInfoController {
    private final RptConfigInfoService rptConfigInfoService;

    public RptConfigInfoController(RptConfigInfoService rptConfigInfoService){
        this.rptConfigInfoService = rptConfigInfoService;
    }

    /**
     * 添加配置信息
     * @param rptConfigInfoDTO
     * @return DefaultCommonResult<Long> 返回id
     */
    @PostMapping("")
    public DefaultCommonResult<Long> insert(@RequestBody @Validated RptConfigInfoDTO rptConfigInfoDTO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,rptConfigInfoService.insert(rptConfigInfoDTO));
    }

    /**
     * 修改配置信息
     * @param id
     * @param rptConfigInfoDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptConfigInfoDTO rptConfigInfoDTO) {
        rptConfigInfoService.update(id, rptConfigInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 配置列表
     * @param baseInfoId
     * @return DefaultCommonResult<PageResultVO<RptBaseInfoVO>>
     */
    @GetMapping("/list/{baseInfoId}")
    public DefaultCommonResult<List<RptConfigInfoVO>> list(
            @PathVariable("baseInfoId") Long baseInfoId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptConfigInfoService.list(baseInfoId));
    }


    /**
     * 详情
     * @param id
     * @return DefaultCommonResult<RptConfigInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<RptConfigInfoVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptConfigInfoService.detail(id));
    }

    /**
     * 删除
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        rptConfigInfoService.delete(ids);
        return DefaultCommonResult.success();
    }

}
