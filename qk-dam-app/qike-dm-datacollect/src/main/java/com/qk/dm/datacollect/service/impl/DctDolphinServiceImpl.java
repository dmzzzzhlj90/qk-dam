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
import com.qk.dm.datacollect.dolphin.service.DolphinProcessDefinitionService;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessService;
import com.qk.dm.datacollect.dolphin.service.DolphinScheduleService;
import com.qk.dm.datacollect.dolphin.service.cron.CronService;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.*;
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
    private final DolphinProcessDefinitionService dolphinProcessDefinitionService;
    private final Map<String, CronService> cronServiceMap;

    public DctDolphinServiceImpl(DolphinProcessService dolphinProcessService,
                                 DolphinScheduleService dolphinScheduleService,
                                 DolphinProcessDefinitionService dolphinProcessDefinitionService,
                                 Map<String, CronService> cronServiceMap) {
        this.dolphinProcessService = dolphinProcessService;
        this.dolphinScheduleService = dolphinScheduleService;
        this.dolphinProcessDefinitionService = dolphinProcessDefinitionService;
        this.cronServiceMap = cronServiceMap;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成参数
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、创建流程定义
        dolphinProcessDefinitionService.createProcessDefinition(name, httpParams, description);
    }

    @Override
    public void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成参数
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、根据详情获取taskCode
        ProcessDefinitionDTO processDefinitionDTO = dolphinProcessService.detailToProcess(dctSchedulerBasicInfoVO.getCode());
        long taskCode = GsonUtil.toJsonArray(processDefinitionDTO.getLocations()).get(0).getAsJsonObject().get("taskCode").getAsLong();
        //4、修改流程定义
        dolphinProcessDefinitionService.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), taskCode, name, httpParams, description);
    }

    @Override
    public void delete(Long processDefinitionCode) {
        dolphinProcessService.delete(processDefinitionCode);
    }

    @Override
    public void release(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dolphinProcessService.release(dctSchedulerReleaseVO.getCode(), dctSchedulerReleaseVO.getReleaseState());
        //更新定时
        updateScheduler(dctSchedulerReleaseVO);
    }

    private void updateScheduler(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        try {
            //如果是上线，更新定时器
            if (Objects.equals(dctSchedulerReleaseVO.getReleaseState(), ProcessDefinition.ReleaseStateEnum.ONLINE)) {
                //查询流程定义详情
                DctSchedulerBasicInfoVO detail = detail(dctSchedulerReleaseVO.getCode());
                DctSchedulerConfigVO schedulerConfig = detail.getSchedulerConfig();
                ScheduleDTO scheduleDTO = dolphinScheduleService.detail(dctSchedulerReleaseVO.getCode());
                if (Objects.equals(schedulerConfig.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode())) {
                    if (scheduleDTO != null) {
                        dolphinScheduleService.update(scheduleDTO.getId(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    } else {
                        dolphinScheduleService.insert(dctSchedulerReleaseVO.getCode(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    }
                    //定时器上线
                    dolphinScheduleService.execute(scheduleDTO.getId(), ProcessDefinition.ReleaseStateEnum.ONLINE);
                } else {
                    //todo 暂时不删除
                }
            }
        } catch (Exception e) {
            //下线
            dolphinProcessService.release(dctSchedulerReleaseVO.getCode(), ProcessDefinition.ReleaseStateEnum.OFFLINE);
            throw new BizException("流程定时更新失败");
        }
    }

    @Override
    public void runing(Long processDefinitionCode) {
        dolphinProcessService.runing(processDefinitionCode, null);
    }

    @Override
    public PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO) {
        //更改查询条件
        DctSchedulerInfoParamsVO.changeSearchValAndDir(schedulerInfoParamsVO);
        ProcessDefinitionResultDTO processDefinitionResultDTO =
                dolphinProcessService.pageList(schedulerInfoParamsVO.getName(),
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
        Object detail = dolphinProcessService.detail(code);
        return DctSchedulerBasicInfoVO.getDctSchedulerBasicInfoVO(detail);
    }
}
