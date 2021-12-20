package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.constant.SchedulerTypeEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.params.dto.DqcSchedulerDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerReleaseDTO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.service.DqcSchedulerExecutorsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author shenpj
 * @date 2021/12/1 5:28 下午
 * @since 1.0.0
 */
@Service
public class DqcSchedulerExecutorsServiceImpl implements DqcSchedulerExecutorsService {

    private final DolphinScheduler dolphinScheduler;
    private final DqcSchedulerBasicInfoService schedulerBasicInfoService;
    private final DqcSchedulerConfigService dqcSchedulerConfigService;

    public DqcSchedulerExecutorsServiceImpl(DolphinScheduler dolphinScheduler,
                                            DqcSchedulerBasicInfoService schedulerBasicInfoService,
                                            DqcSchedulerConfigService dqcSchedulerConfigService) {
        this.dolphinScheduler = dolphinScheduler;
        this.schedulerBasicInfoService = schedulerBasicInfoService;
        this.dqcSchedulerConfigService = dqcSchedulerConfigService;
    }

    @Override
    public void release(DqcSchedulerReleaseDTO infoReleaseDto) {
        DqcSchedulerBasicInfo basicInfo =
                schedulerBasicInfoService.getBasicInfo(infoReleaseDto.getId());
        Long processDefinitionId = basicInfo.getProcessDefinitionId();
        if (infoReleaseDto.getSchedulerState().equals(SchedulerStateEnum.ONLINE.getCode())) {
            dolphinScheduler.online(Long.valueOf(processDefinitionId));
            // 处理定时
            doScheduler(Long.valueOf(processDefinitionId), basicInfo.getJobId());
        } else {
            dolphinScheduler.offline(Long.valueOf(processDefinitionId));
        }
        //TODO 调度状态
        basicInfo.setSchedulerState(infoReleaseDto.getSchedulerState());
        schedulerBasicInfoService.update(basicInfo);
    }

    private void doScheduler(Long processDefinitionCode, String jobId) {
        DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(jobId);
        Integer scheduleId = config.getSchedulerId();
        if (Objects.equals(config.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode())) {
            if (scheduleId != null) {
                updataSchedule(config, scheduleId);
            } else {
                scheduleId = createScheduleAndFlush(processDefinitionCode, config);
            }
            online(scheduleId);
        } else if (scheduleId != null) {
            deleteSchedule(config, scheduleId);
        }
    }

    /**
     * 修改定时
     * @param config
     * @param schedulerId
     */
    private void updataSchedule(DqcSchedulerConfig config, Integer schedulerId) {
        dolphinScheduler.updateSchedule(schedulerId, config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
    }

    /**
     * 创建定时
     * @param processDefinitionCode
     * @param config
     * @return
     */
    private Integer createScheduleAndFlush(Long processDefinitionCode, DqcSchedulerConfig config) {
        Integer scheduleId = dolphinScheduler.createSchedule(processDefinitionCode, config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
        updateConfig(scheduleId, config);
        return scheduleId;
    }

    /**
     * 删除定时
     * @param config
     * @param schedulerId
     */
    private void deleteSchedule(DqcSchedulerConfig config, Integer schedulerId) {
        dolphinScheduler.deleteSchedule(schedulerId);
        updateConfig(null, config);
    }

    /**
     * 更改本地存储定时id
     * @param scheduleId
     * @param config
     */
    public void updateConfig(Integer scheduleId, DqcSchedulerConfig config) {
        config.setSchedulerId(scheduleId);
        dqcSchedulerConfigService.update(config);
    }

    @Override
    public void runing(Long id) {
        DqcSchedulerBasicInfo basicInfo = schedulerBasicInfoService.getBasicInfo(id);
        if (!basicInfo.getSchedulerState().equals(SchedulerStateEnum.ONLINE.getCode())) {
            throw new BizException("请先启动调度！");
        }
        dolphinScheduler.startInstance(Long.valueOf(basicInfo.getProcessDefinitionId()));
    }

    /*********************定时单个操作*************************************************************/

    @Override
    public void update(Integer scheduleId, DqcSchedulerDTO dqcSchedulerDTO) {
        DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(dqcSchedulerDTO.getJobId());
        updataSchedule(config, scheduleId);
    }

    @Override
    public void online(Integer scheduleId) {
        dolphinScheduler.onlineSchedule(scheduleId);
    }

    @Override
    public void offline(Integer scheduleId) {
        dolphinScheduler.offlineSchedule(scheduleId);
    }

    @Override
    public void deleteOne(Integer scheduleId, DqcSchedulerDTO dqcSchedulerDTO) {
        DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(dqcSchedulerDTO.getJobId());
        deleteSchedule(config, scheduleId);
    }

    @Override
    public Object search(Long processDefinitionCode) {
        return dolphinScheduler.searchSchedule(processDefinitionCode);
    }
}
