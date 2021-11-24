package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceData;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceDataBuilder;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.dolphinapi.service.impl.ProcessDefinitionApiServiceImpl;
import com.qk.dm.dataquality.dolphinapi.service.impl.ProcessInstanceServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/22 3:00 下午
 * @since 1.0.0
 */
@Service
public class DolphinScheduler {
  private final ProcessDefinitionApiServiceImpl processDefinitionApiService;
  private final ScheduleApiService scheduleApiService;
  private final ProcessInstanceServiceImpl processInstanceService;

  public DolphinScheduler(
      ProcessDefinitionApiServiceImpl processDefinitionApiService,
      ScheduleApiService scheduleApiService,
      ProcessInstanceServiceImpl processInstanceService) {
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
  public InstanceData detail(Integer processInstanceId) {
    return processInstanceService.detail(processInstanceId);
  }

  /**
   * 查询实例列表最新一跳记录
   *
   * @param processDefinitionId
   * @return
   */
  public InstanceData detailByList(Integer processDefinitionId) {
    InstanceDataBuilder search = processInstanceService.search(processDefinitionId);
    return search.getTotalList().get(0);
  }
}
