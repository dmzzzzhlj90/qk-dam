package com.qk.dm.dolphin.common.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.dolphin.common.client.DolphinApiClient;
import com.qk.dm.dolphin.common.dto.ScheduleDTO;
import com.qk.dm.dolphin.common.dto.ScheduleResultDTO;
import com.qk.dm.dolphin.common.utils.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 定时管理
 * @author shenpj
 * @date 2022/4/20 17:42
 * @since 1.0.0
 */
@Service
public class DolphinScheduleManager {
    private static final Logger LOG = LoggerFactory.getLogger(DolphinScheduleManager.class);
    private final DolphinApiClient dolphinApiClient;

    public DolphinScheduleManager(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    /**
     * 新增定时
     * @param processDefinitionCode 流程定义code
     * @param effectiveTimeStart 开始时间
     * @param ffectiveTimeEnt 结束时间
     * @param cron crom表达式
     */
    public void insert(Long processDefinitionCode, String effectiveTimeStart, String ffectiveTimeEnt, String cron) {
        try {
            dolphinApiClient.schedule_create(
                    processDefinitionCode,
                    effectiveTimeStart,
                    ffectiveTimeEnt,
                    cron);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 创建定时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule insert error ：" + a.getMessage());
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 创建定时报错，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule insert exception error");
        }
    }

    /**
     * 修改定时
     * @param scheduleId 定时id
     * @param effectiveTimeStart 开始时间
     * @param ffectiveTimeEnt 结束时间
     * @param cron crom表达式
     */
    public void update(Integer scheduleId, String effectiveTimeStart, String ffectiveTimeEnt, String cron) {
        try {
            dolphinApiClient.schedule_update(
                    scheduleId,
                    effectiveTimeStart,
                    ffectiveTimeEnt,
                    cron);
        } catch (ApiException a) {
            LOG.error("Dolphin 定时[{}] 修改失败，原因为[{}]", scheduleId, a.getMessage());
            throw new BizException("dolphin schedule update error ：" + a.getMessage());
        } catch (Exception a) {
            LOG.error("Dolphin 定时[{}] 修改报错，原因为[{}]", scheduleId, a.getMessage());
            throw new BizException("dolphin schedule update exception error");
        }
    }

    /**
     * 定时上下线
     * @param scheduleId 定时id
     * @param state 上下线状态 ONLINE-上线 OFFLINE-下线
     */
    public void execute(Integer scheduleId, String state) {
        try {
            if (Objects.equals(state, ProcessDefinition.ReleaseStateEnum.ONLINE.getValue())) {
                dolphinApiClient.schedule_online(scheduleId);
            } else {
                dolphinApiClient.schedule_offline(scheduleId);
            }
        } catch (ApiException a) {
            LOG.error("Dolphin 定时[{}] 更改状态失败，原因为[{}]", scheduleId, a.getMessage());
            throw new BizException("dolphin schedule execute error ：" + a.getMessage());
        }
    }

    /**
     * 删除定时
     * @param scheduleId 定时id
     */
    public void delete(Integer scheduleId) {
        try {
            dolphinApiClient.schedule_delete(scheduleId);
        } catch (ApiException a) {
            LOG.error("Dolphin 定时[{}] 删除失败，原因为[{}]", scheduleId, a.getMessage());
            throw new BizException("dolphin schedule delete error ：" + a.getMessage());
        }
    }

    /**
     * 定时详情
     * @param processDefinitionCode 流程定义code
     * @return ScheduleDTO
     */
    public ScheduleDTO detail(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.schedule_search(processDefinitionCode, 1, 1);
            ScheduleResultDTO scheduleResultDTO = ConstantUtil.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
            return scheduleResultDTO.getTotalList().size() > 0 ? scheduleResultDTO.getTotalList().get(0) : null;
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 查询定时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule detail error ：" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 流程[{}] 查询定时成功，解析时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 查询定时报错，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin schedule detail exception error");
        }
    }
}
