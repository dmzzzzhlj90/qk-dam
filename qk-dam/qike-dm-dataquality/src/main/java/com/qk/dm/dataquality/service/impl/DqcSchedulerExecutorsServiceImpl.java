package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.SchedulerInstanceStateEnum;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.params.dto.DqcSchedulerDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerReleaseDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerRuningDTO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.service.DqcSchedulerExecutorsService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
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

  public DqcSchedulerExecutorsServiceImpl(
      DolphinScheduler dolphinScheduler,
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
    Integer processDefinitionId = basicInfo.getProcessDefinitionId();
    if (infoReleaseDto.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
      dolphinScheduler.online(processDefinitionId);
      // todo 处理定时
      doScheduler(processDefinitionId, basicInfo.getJobId());
    } else {
      dolphinScheduler.offline(processDefinitionId);
    }
    basicInfo.setSchedulerState(infoReleaseDto.getSchedulerState());
    schedulerBasicInfoService.update(basicInfo);
  }

  private void doScheduler(Integer processDefinitionId, String jobId) {
    DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(jobId);
    // 如果之前存在定时，并且是周期调度，修改，否则删除调度
    if (config.getSchedulerId() != null) {
      if (Objects.equals(config.getRunType(), DqcConstant.RUN_TYPE)) {
        dolphinScheduler.updateSchedule(config.getSchedulerId(), config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
        online(config.getSchedulerId());
      } else {
        deleteSchedule(config, config.getSchedulerId());
      }
    } else {
      createSchedule(processDefinitionId, config);
    }
  }

  @Override
  public void runing(DqcSchedulerRuningDTO basicInfoRuningDTO) {
    DqcSchedulerBasicInfo basicInfo =
        schedulerBasicInfoService.getBasicInfo(basicInfoRuningDTO.getId());
    if (basicInfoRuningDTO.getRunInstanceState().equals(SchedulerInstanceStateEnum.RUNING.getCode())) {
      if (!basicInfo.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
        throw new BizException("请先启动调度！");
      }
      dolphinScheduler.startInstance(basicInfo.getProcessDefinitionId());
    } else {
      if (basicInfoRuningDTO.getInstanceId() == null) {
        throw new BizException("停止实例需要实例id！");
      }
      dolphinScheduler.stop(basicInfoRuningDTO.getInstanceId());
    }
    basicInfo.setRunInstanceState(basicInfoRuningDTO.getRunInstanceState());
    schedulerBasicInfoService.update(basicInfo);
    // todo 启动异步查询运行状态，直到成功或失败
  }

  @Override
  public DqcProcessInstanceVO instanceDetailByList(Long id) {
    DqcSchedulerBasicInfo basicInfo = schedulerBasicInfoService.getBasicInfo(id);
    // 获取到最近运行实例
    DqcProcessInstanceVO instanceData =
        dolphinScheduler.detailByList(basicInfo.getProcessDefinitionId());
    // 保存状态
    InstanceStateTypeEnum instanceStateTypeEnum =
        InstanceStateTypeEnum.fromValue(instanceData.getState());
    instanceData.setStateName(instanceStateTypeEnum.getSchedulerInstanceStateEnum().getValue());
    basicInfo.setRunInstanceState(instanceStateTypeEnum.getSchedulerInstanceStateEnum().getCode());
    schedulerBasicInfoService.update(basicInfo);
    return instanceData;
  }

  @Override
  public void createSchedule(DqcSchedulerDTO dqcSchedulerDTO) {
    createScheduleAndFlush(dqcSchedulerDTO.getProcessDefinitionId(), dqcSchedulerConfigService.getConfig(dqcSchedulerDTO.getJobId()));
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerDTO dqcSchedulerDTO) {
    DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(dqcSchedulerDTO.getJobId());
    dolphinScheduler.updateSchedule(scheduleId, config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
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
  public Object search(Integer processDefinitionId) {
    return dolphinScheduler.searchSchedule(processDefinitionId);
  }

  public void createSchedule(Integer processDefinitionId, DqcSchedulerConfig config) {
    if (Objects.equals(config.getRunType(), DqcConstant.RUN_TYPE)) {
      Integer scheduleId = createScheduleAndFlush(processDefinitionId, config);
      online(scheduleId);
    }
  }

  private Integer createScheduleAndFlush(Integer processDefinitionId, DqcSchedulerConfig config) {
    Integer scheduleId = dolphinScheduler.createSchedule(processDefinitionId, config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
    updateConfig(scheduleId, config);
    return scheduleId;
  }

  private void deleteSchedule(DqcSchedulerConfig config, Integer schedulerId) {
    dolphinScheduler.deleteSchedule(schedulerId);
    updateConfig(null, config);
  }

  public void updateConfig(Integer scheduleId, DqcSchedulerConfig config) {
    config.setSchedulerId(scheduleId);
    dqcSchedulerConfigService.update(config);
  }
}
