package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import org.springframework.stereotype.Service;

/**
 * 调度引擎Dolphin Scheduler 流程定义相关操作
 *
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public interface ProcessDefinitionApiService {

  void save(DqcSchedulerInfoVO dqcSchedulerInfoVO);

  void release(Integer processDefinitionId, Integer releaseState);

  void delete(Integer processDefinitionId);

  void copy(Integer processDefinitionId);

  void startCheck(Integer processDefinitionId);

  void startInstance(Integer processDefinitionId);
}
