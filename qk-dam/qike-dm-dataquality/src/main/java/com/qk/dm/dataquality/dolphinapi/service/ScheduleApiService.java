package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.ScheduleListPageVo;

/**
 * @author shenpj
 * @date 2021/11/16 4:39 下午
 * @since 1.0.0
 */
public interface ScheduleApiService {

  void create(DqcSchedulerInfoVO dqcSchedulerInfoVO);

  void update(Integer scheduleId, DqcSchedulerInfoVO dqcSchedulerInfoVO);

  void online(Integer scheduleId);

  void offline(Integer scheduleId);

  void deleteOne(Integer scheduleId);

  ScheduleListPageVo search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal);
}
