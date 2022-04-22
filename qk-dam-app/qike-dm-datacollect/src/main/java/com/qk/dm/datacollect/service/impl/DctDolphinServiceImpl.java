package com.qk.dm.datacollect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dto.ProcessDefinitionResultDTO;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.service.DolphinProcessService;
import com.qk.dm.datacollect.util.DctConstant;
import com.qk.dm.datacollect.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
@Service
public class DctDolphinServiceImpl implements DctDolphinService {

    private final DolphinProcessService dolphinProcessService;

    @Value("${dolphinscheduler.task.projectCode}")
    private Long projectCode;

    public DctDolphinServiceImpl(DolphinProcessService dolphinProcessService) {
        this.dolphinProcessService = dolphinProcessService;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getName();
        String url = "http://www.baidu.com";
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        String httpMethod = "POST";
        String description = dctSchedulerBasicInfoVO.getDescription();

        ProcessDefinitionDTO processDefinition = dolphinProcessService.createProcessDefinition(projectCode, name, url, httpParams, httpMethod, description);
        System.out.println("processDefinition:" + processDefinition);


        //创建完流程实例后保存到本地，方便根据分类查询
        String dirId = dctSchedulerBasicInfoVO.getDirId();
    }

    @Override
    public void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getName();
        String url = "http://www.baidu.com";
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        String httpMethod = "POST";
        String description = dctSchedulerBasicInfoVO.getDescription();

        ProcessDefinitionDTO processDefinitionDTO = dolphinProcessService.detailToProcess(dctSchedulerBasicInfoVO.getCode(), projectCode);
        long taskCode = GsonUtil.toJsonArray(processDefinitionDTO.getLocations()).get(0).getAsJsonObject().get("taskCode").getAsLong();

        dolphinProcessService.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), projectCode,taskCode, name, url, httpParams, httpMethod, description);
    }

    @Override
    public void delete(Long processDefinitionCode) {
        dolphinProcessService.delete(processDefinitionCode, projectCode);
    }

    @Override
    public void release(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dolphinProcessService.release(dctSchedulerReleaseVO.getProcessDefinitionCode(), projectCode, dctSchedulerReleaseVO.getReleaseState());
    }

    @Override
    public void runing(Long processDefinitionCode) {
        dolphinProcessService.runing(processDefinitionCode, projectCode, null);
    }

    @Override
    public PageResultVO<DctSchedulerInfoVO> searchPageList(DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        ProcessDefinitionResultDTO processDefinitionResultDTO =
                dolphinProcessService.list(projectCode, schedulerInfoParamsVO.getJobName(),
                        schedulerInfoParamsVO.getPagination().getPage(), schedulerInfoParamsVO.getPagination().getSize());
        List<ProcessDefinitionDTO> processDefinitionDTOS = processDefinitionResultDTO.getTotalList();
        List<DctSchedulerInfoVO> basicInfoVOList = processDefinitionDTOS.stream().map(
                i -> {
                    try {
                        return DctConstant.changeObjectToClass(i, DctSchedulerInfoVO.class);
                    } catch (JsonProcessingException a) {
                        throw new BizException("dolphin process toClass error :" + a.getMessage());
                    }
                }).collect(Collectors.toList());
        return new PageResultVO<>(
                processDefinitionResultDTO.getTotal(),
                schedulerInfoParamsVO.getPagination().getPage(),
                schedulerInfoParamsVO.getPagination().getSize(),
                basicInfoVOList);
    }

    @Override
    public DctSchedulerBasicInfoVO detail(Long code) {
        return dolphinProcessService.detail(code,projectCode);
    }


}
