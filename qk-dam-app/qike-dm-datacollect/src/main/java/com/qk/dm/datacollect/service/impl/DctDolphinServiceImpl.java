package com.qk.dm.datacollect.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionResultDTO;
import com.qk.dm.datacollect.dolphin.dto.ScheduleDTO;
import com.qk.dm.datacollect.dolphin.dto.SchedulerTypeEnum;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessService;
import com.qk.dm.datacollect.dolphin.service.DolphinScheduleService;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
@Service
public class DctDolphinServiceImpl implements DctDolphinService {

    private final DolphinProcessService dolphinProcessService;
    private final DolphinScheduleService dolphinScheduleService;

    @Value("${dolphinscheduler.task.projectCode}")
    private Long projectCode;

    public DctDolphinServiceImpl(DolphinProcessService dolphinProcessService, DolphinScheduleService dolphinScheduleService) {
        this.dolphinProcessService = dolphinProcessService;
        this.dolphinScheduleService = dolphinScheduleService;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String url = "http://www.baidu.com";
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        String httpMethod = "GET";
        String description = dctSchedulerBasicInfoVO.getDescription();

        ProcessDefinitionDTO processDefinition = dolphinProcessService.createProcessDefinition(projectCode, name, url, httpParams, httpMethod, description);
        System.out.println("processDefinition:" + processDefinition);

        //创建定时
        createScheduler(dctSchedulerBasicInfoVO.getSchedulerConfig(), processDefinition.getCode());
    }

    @Override
    public void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String url = "http://www.baidu.com";
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        String httpMethod = "GET";
        String description = dctSchedulerBasicInfoVO.getDescription();

        ProcessDefinitionDTO processDefinitionDTO = dolphinProcessService.detailToProcess(dctSchedulerBasicInfoVO.getCode(), projectCode);
        long taskCode = GsonUtil.toJsonArray(processDefinitionDTO.getLocations()).get(0).getAsJsonObject().get("taskCode").getAsLong();

        dolphinProcessService.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), projectCode, taskCode, name, url, httpParams, httpMethod, description);

        //修改定时
        updateScheduler(dctSchedulerBasicInfoVO.getSchedulerConfig(), dctSchedulerBasicInfoVO.getCode());

    }


    private void createScheduler(DctSchedulerConfigVO schedulerConfig, Long code) {
        if (Objects.equals(schedulerConfig.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode())) {
            //首先上线
            dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.ONLINE);
            try {
                //创建定时
                dolphinScheduleService.insert(code, projectCode, schedulerConfig);
                //下线
                dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.OFFLINE);
            } catch (Exception e) {
                //下线
                dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.OFFLINE);
                throw new BizException(e.getMessage());
            }
        }
    }

    private void updateScheduler(DctSchedulerConfigVO schedulerConfig, Long code) {
        ScheduleDTO scheduleDTO = dolphinScheduleService.detail(code, projectCode);
        if (scheduleDTO != null) {
            updateScheduler(schedulerConfig, code, scheduleDTO.getId());
        } else {
            //创建定时
            createScheduler(schedulerConfig, code);
        }
    }

    private void updateScheduler(DctSchedulerConfigVO schedulerConfig, Long code, Integer scheduleId) {
        //上线
        dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.ONLINE);
        try {
            //修改定时
            if (Objects.equals(schedulerConfig.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode())) {
                dolphinScheduleService.update(scheduleId, projectCode, schedulerConfig);
            } else {
                dolphinScheduleService.delete(scheduleId, projectCode);
            }
            //下线
            dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.OFFLINE);
        } catch (Exception e) {
            //下线
            dolphinProcessService.release(code, projectCode, ProcessDefinition.ReleaseStateEnum.OFFLINE);
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public void delete(Long processDefinitionCode) {
        dolphinProcessService.delete(processDefinitionCode, projectCode);
    }

    @Override
    public void release(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dolphinProcessService.release(dctSchedulerReleaseVO.getCode(), projectCode, dctSchedulerReleaseVO.getReleaseState());

        //todo 如果是上线，顺便上定时器
        if (Objects.equals(dctSchedulerReleaseVO.getReleaseState(), ProcessDefinition.ReleaseStateEnum.ONLINE)) {
            ScheduleDTO scheduleDTO = dolphinScheduleService.detail(dctSchedulerReleaseVO.getCode(), projectCode);
            if (scheduleDTO != null) {
                //定时器上线
                dolphinScheduleService.execute(scheduleDTO.getId(), projectCode, ProcessDefinition.ReleaseStateEnum.ONLINE);
            }
        }

    }

    @Override
    public void runing(Long processDefinitionCode) {
        dolphinProcessService.runing(processDefinitionCode, projectCode, null);
    }

    @Override
    public PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO) {
        //更改查询条件
        DctSchedulerInfoParamsVO.changeSearchValAndDir(schedulerInfoParamsVO);
        ProcessDefinitionResultDTO processDefinitionResultDTO =
                dolphinProcessService.pageList(projectCode, schedulerInfoParamsVO.getName(),
                        schedulerInfoParamsVO.getPagination().getPage(), schedulerInfoParamsVO.getPagination().getSize());
        List<Map<String, Object>> processDefinitionListMap = BeanMapUtils.changeBeansToList(processDefinitionResultDTO.getTotalList());
        List<DctSchedulerInfoVO> basicInfoList = BeanMapUtils.changeListToBeans(processDefinitionListMap, DctSchedulerInfoVO.class);
        //把name中的分类去除
        DctSchedulerInfoVO.changeName(basicInfoList);
        return new PageResultVO<>(
                processDefinitionResultDTO.getTotal(),
                schedulerInfoParamsVO.getPagination().getPage(),
                schedulerInfoParamsVO.getPagination().getSize(),
                basicInfoList);
    }

    @Override
    public DctSchedulerBasicInfoVO detail(Long code) {
        Object detail = dolphinProcessService.detail(code, projectCode);
        return DctSchedulerBasicInfoVO.getDctSchedulerBasicInfoVO(detail);
    }
}
