package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.dto.ScheduleResultDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;

/**
 * @author shenpj
 * @date 2021/11/16 4:39 下午
 * @since 1.0.0
 */
public interface ScheduleApiService {

  void create(Integer processDefinitionId, DqcSchedulerConfigVO dqcSchedulerConfigVO);

  void update(Integer scheduleId, DqcSchedulerConfigVO dqcSchedulerConfigVO);

  void online(Integer scheduleId);

  void offline(Integer scheduleId);

  void deleteOne(Integer scheduleId);

  ScheduleResultDTO search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal);
}
