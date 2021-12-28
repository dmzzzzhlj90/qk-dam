package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.dto.ScheduleDeleteDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleSearchDTO;

import java.util.Date;

/**
 * @author shenpj
 * @date 2021/11/16 4:39 下午
 * @since 1.0.0
 */
public interface ScheduleApiService {

  void create(Long processDefinitionCode, Date effectiveTimeStart, Date effectiveTimeEnt, String cron);

  void update(Integer scheduleId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron);

  void online(Integer scheduleId);

  void offline(Integer scheduleId);

  void deleteOne(ScheduleDeleteDTO scheduleDeleteDTO);

  ScheduleResultDTO search(ScheduleSearchDTO scheduleSearchDTO);
}
