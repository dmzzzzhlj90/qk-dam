package com.qk.dm.dataquality.service;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.SchedulerInstanceStateEnum;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.params.dto.DqcSchedulerDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerReleaseDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerRuningDTO;
import com.qk.dm.dataquality.service.impl.DolphinScheduler;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import org.springframework.stereotype.Service;

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
      // todo 这里定时是否跟着上线
    } else {
      dolphinScheduler.offline(processDefinitionId);
    }
    basicInfo.setSchedulerState(infoReleaseDto.getSchedulerState());
    schedulerBasicInfoService.update(basicInfo);
  }

  @Override
  public void runing(DqcSchedulerRuningDTO basicInfoRuningDTO) {
    DqcSchedulerBasicInfo basicInfo =
        schedulerBasicInfoService.getBasicInfo(basicInfoRuningDTO.getId());
    if (basicInfoRuningDTO
        .getRunInstanceState()
        .equals(SchedulerInstanceStateEnum.RUNING.getCode())) {
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
  public void create(DqcSchedulerDTO dqcSchedulerDTO) {
    DqcSchedulerBasicInfo info =
        schedulerBasicInfoService.getBasicInfo(dqcSchedulerDTO.getId());
    if (!info.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
      throw new BizException("请先启动调度！");
    }
    DqcSchedulerConfig config = dqcSchedulerConfigService.getConfig(dqcSchedulerDTO.getJobId());

    Integer scheduleId = dolphinScheduler.createSchedule(info.getProcessDefinitionId(), config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
    config.setSchedulerId(scheduleId);
    dqcSchedulerConfigService.update(config);
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerConfigVO config) {

    dolphinScheduler.updateSchedule(scheduleId,config.getEffectiveTimeStart(), config.getEffectiveTimeEnt(), config.getCron());
  }


}
