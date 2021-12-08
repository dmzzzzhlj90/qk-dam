package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.service.RptBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化_基础配置信息
 * @author zys
 * @date 2021/12/8 12:11
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/info")
public class RptInfoController {

    private final RptBaseInfoService rptBaseInfoService;

    public RptInfoController(RptBaseInfoService rptBaseInfoService){
        this.rptBaseInfoService = rptBaseInfoService;
    }

    /**
     * 添加基础信息
     * @param rptBaseInfoDTO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptBaseInfoDTO rptBaseInfoDTO){
        rptBaseInfoService.insert(rptBaseInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 配置爬虫信息
     * @param rptBaseInfoDTO
     * @return
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoService.update(id, rptBaseInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 待配列表
     * @param rptBaseInfoDTO
     * @return
     */
    @GetMapping("/waiting")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> waitingList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoDTO.setStatus(RptConstant.WAITING);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO));
    }


    /**
     * 爬虫列表
     * @param rptBaseInfoDTO
     * @return
     */
    @GetMapping("/reptile")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> reptileList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoDTO.setStatus(RptConstant.REPTILE);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO));
    }

    /**
     * 历史列表
     * @param rptBaseInfoDTO
     * @return
     */
    @GetMapping("/history")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> historyList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoDTO.setStatus(RptConstant.HISTORY);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO));
    }

}