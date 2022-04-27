package com.qk.dm.datacollect.dolphin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.client.DolphinApiClient;
import com.qk.dm.datacollect.dolphin.dto.ScheduleDTO;
import com.qk.dm.datacollect.dolphin.dto.ScheduleResultDTO;
import com.qk.dm.datacollect.dolphin.service.DolphinScheduleService;
import com.qk.dm.datacollect.dolphin.service.cron.CronService;
import com.qk.dm.datacollect.util.DctConstant;
import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(DolphinScheduleServiceImpl.class);
    private final DolphinApiClient dolphinApiClient;

    private final Map<String, CronService> cronServiceMap;

    public DolphinScheduleServiceImpl(DolphinApiClient dolphinApiClient, Map<String, CronService> cronServiceMap) {
        this.dolphinApiClient = dolphinApiClient;
        this.cronServiceMap = cronServiceMap;
    }

    @Override
    public void insert(Long processDefinitionCode, Long projectCode, DctSchedulerConfigVO dqcSchedulerConfigVO) {
        try {
            String cron = dqcSchedulerConfigVO.generateCron(dqcSchedulerConfigVO, cronServiceMap);
            dolphinApiClient.schedule_create(
                    processDefinitionCode,
                    projectCode,
                    dqcSchedulerConfigVO.getEffectiveTimeStart(),
                    dqcSchedulerConfigVO.getEffectiveTimeEnt(),
                    cron);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 创建定时失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule insert error");
        }catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 创建定时报错，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule insert exception error");
        }
    }

    @Override
    public void update(Integer scheduleId, Long projectCode, DctSchedulerConfigVO dqcSchedulerConfigVO) {
        try {
            String cron = dqcSchedulerConfigVO.generateCron(dqcSchedulerConfigVO, cronServiceMap);
            dolphinApiClient.schedule_update(
                    scheduleId,
                    projectCode,
                    dqcSchedulerConfigVO.getEffectiveTimeStart(),
                    dqcSchedulerConfigVO.getEffectiveTimeEnt(),
                    cron);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 定时[{}] 修改失败，原因为[{}]", projectCode, scheduleId, a.getMessage());
            throw new BizException("dolphin schedule update error");
        }catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 定时[{}] 修改报错，原因为[{}]", projectCode, scheduleId, a.getMessage());
            throw new BizException("dolphin schedule update exception error");
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
            LOG.error("Dolphin 项目[{}] 定时[{}] 更改状态失败，原因为[{}]", projectCode, scheduleId, a.getMessage());
            throw new BizException("dolphin schedule execute error");
        }
    }

    @Override
    public void delete(Integer scheduleId, Long projectCode) {
        try {
            dolphinApiClient.schedule_delete(scheduleId, projectCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 定时[{}] 删除失败，原因为[{}]", projectCode, scheduleId, a.getMessage());
            throw new BizException("dolphin schedule delete error");
        }
    }

    @Override
    public ScheduleDTO detail(Long processDefinitionCode, Long projectCode) {
        try {
            Result result = dolphinApiClient.schedule_search(processDefinitionCode, projectCode, 1, 1);
            ScheduleResultDTO scheduleResultDTO = DctConstant.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
            return scheduleResultDTO.getTotalList().size() > 0 ? scheduleResultDTO.getTotalList().get(0) : null;
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询定时失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule detail error");
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询定时成功，解析时失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询定时报错，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule detail exception error");
        }
    }

}
