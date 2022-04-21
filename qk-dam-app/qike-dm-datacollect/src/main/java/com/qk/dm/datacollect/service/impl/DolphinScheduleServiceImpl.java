package com.qk.dm.datacollect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dto.ScheduleDTO;
import com.qk.dm.datacollect.dto.ScheduleResultDTO;
import com.qk.dm.datacollect.dolphin.DolphinApiClient;
import com.qk.dm.datacollect.service.DolphinScheduleService;
import com.qk.dm.datacollect.service.cron.CronService;
import com.qk.dm.datacollect.util.DctConstant;
import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2022/4/20 17:42
 * @since 1.0.0
 */
@Service
public class DolphinScheduleServiceImpl implements DolphinScheduleService {
    private final DolphinApiClient dolphinApiClient;

    private final Map<String, CronService> cronServiceMap;

    public DolphinScheduleServiceImpl(DolphinApiClient dolphinApiClient, Map<String, CronService> cronServiceMap) {
        this.dolphinApiClient = dolphinApiClient;
        this.cronServiceMap = cronServiceMap;
    }

    @Override
    public void insert(Long projectCode,Long processDefinitionCode, DctSchedulerConfigVO dqcSchedulerConfigVO) {
        try {
            String cron = dqcSchedulerConfigVO.generateCron(dqcSchedulerConfigVO, cronServiceMap);
            dolphinApiClient.schedule_create(
                    processDefinitionCode,
                    projectCode,
                    dqcSchedulerConfigVO.getEffectiveTimeStart(),
                    dqcSchedulerConfigVO.getEffectiveTimeEnt(),
                    cron);
        } catch (ApiException a) {
            throw new BizException("dolphin insert error :" + a.getMessage());
        }
    }

    @Override
    public void update(Long projectCode,Integer scheduleId, DctSchedulerConfigVO dqcSchedulerConfigVO) {
        try {
            String cron = dqcSchedulerConfigVO.generateCron(dqcSchedulerConfigVO, cronServiceMap);
            dolphinApiClient.schedule_update(
                    scheduleId,
                    projectCode,
                    dqcSchedulerConfigVO.getEffectiveTimeStart(),
                    dqcSchedulerConfigVO.getEffectiveTimeEnt(),
                    cron);
        } catch (ApiException a) {
            throw new BizException("dolphin update error :" + a.getMessage());
        }
    }

    @Override
    public void execute(Integer scheduleId, Long projectCode, ProcessDefinition.ReleaseStateEnum state) {
        try {
            if (Objects.equals(state, ProcessDefinition.ReleaseStateEnum.ONLINE)) {
                dolphinApiClient.schedule_online(scheduleId, projectCode);
            } else {
                dolphinApiClient.schedule_offline(scheduleId, projectCode);
            }
        } catch (ApiException a) {
            throw new BizException("dolphin execute error :" + a.getMessage());
        }
    }

    @Override
    public void delete(Integer scheduleId, Long projectCode) {
        try {
            dolphinApiClient.schedule_delete(scheduleId, projectCode);
        } catch (ApiException a) {
            throw new BizException("dolphin delete error :" + a.getMessage());
        }
    }

    @Override
    public ScheduleDTO detail(Long processDefinitionCode, Long projectCode) {
        try {
            Result result = dolphinApiClient.schedule_search(processDefinitionCode, projectCode, 1, 1);
            ScheduleResultDTO scheduleResultDTO = DctConstant.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
            return scheduleResultDTO.getTotalList() != null ? scheduleResultDTO.getTotalList().get(0) : null;
        } catch (ApiException a) {
            throw new BizException("dolphin detail error :" + a.getMessage());
        } catch (JsonProcessingException a) {
            throw new BizException("dolphin toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin exception error :" + a.getMessage());
        }
    }

}
