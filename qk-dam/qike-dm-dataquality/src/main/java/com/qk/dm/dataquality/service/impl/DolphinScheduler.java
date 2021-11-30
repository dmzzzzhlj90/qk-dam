package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.dolphinapi.service.ProcessInstanceService;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/22 3:00 下午
 * @since 1.0.0
 */
@Service
public class DolphinScheduler {
  private final ProcessDefinitionApiService processDefinitionApiService;
  private final ScheduleApiService scheduleApiService;
  private final ProcessInstanceService processInstanceService;

  public DolphinScheduler(
      ProcessDefinitionApiService processDefinitionApiService,
      ScheduleApiService scheduleApiService,
      ProcessInstanceService processInstanceService) {
    this.processDefinitionApiService = processDefinitionApiService;
    this.scheduleApiService = scheduleApiService;
    this.processInstanceService = processInstanceService;
  }

  /**
   * 流程定义上线，定时上线
   *
   * @param processDefinitionId
   * @param scheduleId
   */
  public void online(Integer processDefinitionId, Integer scheduleId) {
    // 流程定义上线
    processDefinitionApiService.release(processDefinitionId, DqcConstant.PUBLISH_STATE_UP);
    // 定时上线
    if (scheduleId != null) {
      scheduleApiService.online(scheduleId);
    }
  }

  /**
   * 流程定义下线，定时自动下线
   *
   * @param processDefinitionId
   */
  public void offline(Integer processDefinitionId) {
    // 流程定义下线
    processDefinitionApiService.release(processDefinitionId, DqcConstant.PUBLISH_STATE_DOWN);
  }

  /**
   * 运行
   *
   * @param processDefinitionId
   */
  public void startInstance(Integer processDefinitionId) {
    // 流程定义上线
    processDefinitionApiService.release(processDefinitionId, DqcConstant.PUBLISH_STATE_UP);
    processDefinitionApiService.startCheck(processDefinitionId);
    processDefinitionApiService.startInstance(processDefinitionId);
  }

  public void stop(Integer processDefinitionId) {
    // 停止实例
    processInstanceService.execute(processDefinitionId, ExecuteTypeEnum.STOP.getCode());
  }

  /**
   * 删除流程
   *
   * @param processDefinitionId
   */
  public void deleteOne(Integer processDefinitionId) {
    processDefinitionApiService.deleteOne(processDefinitionId);
  }

  /**
   * 查询实例详情
   *
   * @param processInstanceId
   * @return
   */
  public DqcProcessInstanceVO detail(Integer processInstanceId) {
    return DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(
        processInstanceService.detail(processInstanceId));
  }

  /**
   * 查询实例列表最新一条记录
   *
   * @param processDefinitionId
   * @return
   */
  public DqcProcessInstanceVO detailByList(Integer processDefinitionId) {
    ProcessInstanceSearchDTO instanceSearchDTO =
        ProcessInstanceSearchDTO.builder()
            .processDefinitionId(processDefinitionId)
            .pageNo(1)
            .pageSize(1)
            .build();
    ProcessInstanceResultDTO search = processInstanceService.search(instanceSearchDTO);
    return DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(search.getTotalList().get(0));
  }
}
