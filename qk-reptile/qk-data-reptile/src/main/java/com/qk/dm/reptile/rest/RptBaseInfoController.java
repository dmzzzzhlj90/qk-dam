package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.enums.TimeIntervalEnum;
import com.qk.dm.reptile.params.dto.*;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.params.vo.TimeIntervalVO;
import com.qk.dm.reptile.service.RptBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 爬虫数据采集基础配置信息
 * @author wangzp
 * @date 2021/12/8 12:11
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/base/info")
public class RptBaseInfoController {

    private final RptBaseInfoService rptBaseInfoService;

    public RptBaseInfoController(RptBaseInfoService rptBaseInfoService){
        this.rptBaseInfoService = rptBaseInfoService;
    }

    /**
     * 添加基础信息
     * @param rptBaseInfoDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptBaseInfoDTO rptBaseInfoDTO){
        rptBaseInfoDTO.setStatus(RptConstant.WAITING);
        rptBaseInfoService.insert(rptBaseInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改基础信息
     * @param id 基础信息id
     * @param rptBaseInfoDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptBaseInfoDTO rptBaseInfoDTO) {
        rptBaseInfoService.update(id, rptBaseInfoDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改运行状态(启动或关闭)
     * @param rptRunStatusDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/run/status")
    public DefaultCommonResult updateRunStatus(
            @RequestBody @Validated RptRunStatusDTO rptRunStatusDTO) {
        rptBaseInfoService.updateRunStatus(rptRunStatusDTO.getId(), rptRunStatusDTO.getRunStatus());
        return DefaultCommonResult.success();
    }

    /**
     * 待配列表
     * @param rptBaseInfoDTO
     * @return DefaultCommonResult<PageResultVO<RptBaseInfoVO>>
     */
    @PostMapping("/waiting")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> waitingList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        rptBaseInfoDTO.setStatus(RptConstant.WAITING);
        rptBaseInfoDTO.setDelFlag(RptConstant.REDUCTION_STATUS);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO,authorizedClient));
    }


    /**
     * 爬虫列表
     * @param rptBaseInfoDTO
     * @return DefaultCommonResult<PageResultVO<RptBaseInfoVO>>
     */
    @PostMapping("/reptile")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> reptileList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        rptBaseInfoDTO.setStatus(RptConstant.REPTILE);
        rptBaseInfoDTO.setDelFlag(RptConstant.REDUCTION_STATUS);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO,authorizedClient));
    }

    /**
     * 历史列表
     * @param rptBaseInfoDTO
     * @return DefaultCommonResult<PageResultVO<RptBaseInfoVO>>
     */
    @PostMapping("/history")
    public DefaultCommonResult<PageResultVO<RptBaseInfoVO>> historyList(
            @RequestBody RptBaseInfoDTO rptBaseInfoDTO,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        rptBaseInfoDTO.setDelFlag(RptConstant.DEL_STATUS);
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.listByPage(rptBaseInfoDTO,authorizedClient));
    }
    /**
     * 详情
     * @param id
     * @return DefaultCommonResult<RptBaseInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<RptBaseInfoVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, rptBaseInfoService.detail(id));
    }

    /**
     * 删除
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        rptBaseInfoService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 还原
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/reduction/{ids}")
    public DefaultCommonResult reduction(@PathVariable("ids") String ids){
        rptBaseInfoService.reduction(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 手动执行调用爬虫接口
     * @param id
     * @return DefaultCommonResult
     */
    @GetMapping("/execution/{id}")
    public DefaultCommonResult execution(@PathVariable("id") Long id){
        rptBaseInfoService.execution(id);
        return DefaultCommonResult.success();
    }

    /**
     * 定时执行调用爬虫接口
     * @return DefaultCommonResult
     */
    @GetMapping("/timed/execution")
    public DefaultCommonResult timedExecution(){
        rptBaseInfoService.timedExecution(null);
        return DefaultCommonResult.success();
    }

    /**
     * 配置复制
     * @param rptCopyConfig
     * @return DefaultCommonResult
     */
    @PostMapping("/copy/config")
    public DefaultCommonResult copyConfig(@RequestBody @Validated RptCopyConfigDTO rptCopyConfig){
        rptBaseInfoService.copyConfig(rptCopyConfig.getSourceId(),rptCopyConfig.getTargetId());
        return DefaultCommonResult.success();
    }

    /**
     * 获取定时时间间隔
     * @return
     */
    @GetMapping("/time/interval")
    public DefaultCommonResult<List<TimeIntervalVO>> getTimeInterval(){
        return DefaultCommonResult.success(ResultCodeEnum.OK,TimeIntervalEnum.enumToList());
    }

    /**
     * 修改定时时间间隔
     * @param timeIntervalDTO
     * @return
     */
    @PostMapping("/time/interval")
    public DefaultCommonResult updateTimeInterval(@RequestBody @Validated TimeIntervalDTO timeIntervalDTO) {
        rptBaseInfoService.updateTimeInterval(timeIntervalDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 分配任务
     * @param rptAssignedTaskDTO
     * @return
     */
    @PostMapping("/assigned/task")
    public DefaultCommonResult assignedTask(@RequestBody @Validated RptAssignedTaskDTO rptAssignedTaskDTO) {
        rptBaseInfoService.assignedTasks(rptAssignedTaskDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改状态  0表示待配 1表示爬虫 2表示历史
     * @param id
     * @param status  0表示待配 1表示爬虫 2表示历史
     * @return DefaultCommonResult
     */
    //@PutMapping("/{id}/{status}")
//    public DefaultCommonResult updateStatus(
//            @PathVariable("id") Long id,@PathVariable("status") Integer status ) {
//        rptBaseInfoService.updateStatus(id, status);
//        return DefaultCommonResult.success();
//    }


}