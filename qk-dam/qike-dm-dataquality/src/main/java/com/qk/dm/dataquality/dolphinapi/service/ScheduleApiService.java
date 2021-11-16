package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.ScheduleListPageVo;

/**
 * @author shenpj
 * @date 2021/11/16 4:39 下午
 * @since 1.0.0
 */
public interface ScheduleApiService {
  void createSchedule(DqcSchedulerInfoVO dqcSchedulerInfoVO);

  void updateSchedule(Integer scheduleId, DqcSchedulerInfoVO dqcSchedulerInfoVO);

  void online(Integer scheduleId);

  void offline(Integer scheduleId);

  void deleteScheduleById(Integer scheduleId);

  ScheduleListPageVo queryScheduleListPaging(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal);
}
