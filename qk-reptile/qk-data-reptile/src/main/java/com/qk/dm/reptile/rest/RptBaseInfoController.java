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
 * 爬虫数据采集基础配置信息
 * @author wangzp
 * @date 2021/12/8 12:11
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/info")
public class RptBaseInfoController {

    private final RptBaseInfoService rptBaseInfoService;

    public RptBaseInfoController(RptBaseInfoService rptBaseInfoService){
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
     * @param id 基础信息id
     * @param runStatus 数据启动状态（0表示启动、1表示未启动）
     * @param rptBaseInfoDTO
     * @return
     */
    @PutMapping("/config/{id}/{runStatus}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @PathVariable("runStatus") Integer runStatus, @RequestBody @Validated RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoDTO.setRunStatus(runStatus);
        rptBaseInfoService.update(id, rptBaseInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 待配列表
     * @param rptBaseInfoDTO
     * @return
     */
    @PostMapping("/waiting")
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
    @PostMapping("/reptile")
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
    @PostMapping("/history")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> historyList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoDTO.setStatus(RptConstant.HISTORY);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO));
    }
    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<RptBaseInfoVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.detail(id));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    private DefaultCommonResult delete(@PathVariable("ids") String ids){
        rptBaseInfoService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 修改状态  0表示待配 1表示爬虫 2表示历史
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/{id}/{status}")
    public DefaultCommonResult updateStatus(
            @PathVariable("id") Long id,@PathVariable("status") Integer status ) {
        rptBaseInfoService.updateStatus(id, status);
        return DefaultCommonResult.success();
    }


}