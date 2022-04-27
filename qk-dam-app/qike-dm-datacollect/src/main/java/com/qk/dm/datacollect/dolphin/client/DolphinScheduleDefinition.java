package com.qk.dm.datacollect.dolphin.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.datacenter.model.ProcessInstance;
import lombok.Data;

import java.util.Map;

/**
 * @author shenpj
 * @date 2022/4/20 15:17
 * @since 1.0.0
 */
@Data
public class DolphinScheduleDefinition {
    private static final String SCHEDULE_TIME_START = "startTime";
    private static final String SCHEDULE_TIME_END = "endTime";
    private static final String SCHEDULE_CRON = "crontab";

    Long environmentCode;
    String failureStrategy;
    String processInstancePriority;
    String schedule;
    Integer warningGroupId;
    String warningType;
    String workerGroup;

    public DolphinScheduleDefinition(String effectiveTimeStart,String effectiveTimeEnt,String cron) {
        this.failureStrategy = ProcessInstance.FailureStrategyEnum.CONTINUE.getValue();
        this.processInstancePriority = ProcessInstance.ProcessInstancePriorityEnum.MEDIUM.getValue();
        this.schedule = schedule(effectiveTimeStart, effectiveTimeEnt, cron);
        this.warningGroupId = 0;
        this.warningType = ProcessInstance.WarningTypeEnum.NONE.getValue();
        this.workerGroup = "default";
    }

    private static String schedule(String effectiveTimeStart, String effectiveTimeEnt, String cron) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> m = Map.of(
                SCHEDULE_TIME_START,
                effectiveTimeStart,
                SCHEDULE_TIME_END,
                effectiveTimeEnt,
                SCHEDULE_CRON,
                cron,
                "timezoneId",
                "Asia/Shanghai");
        try {
            return objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
