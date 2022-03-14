package com.qk.dm.dataingestion.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhudaoming
 */
@Component
@Data
@ConfigurationProperties("dolphinscheduler.task")
public class DolphinTaskDefinitionPropertiesBean {
    private long code;
    private String name;
    @Expose
    private String tenantCode;
    private String description;
    private String taskType;
    private TaskParams taskParams;
    private String flag;
    private String taskPriority;
    private String workerGroup;
    private String failRetryTimes;
    private String failRetryInterval;
    private String timeoutFlag;
    private String timeoutNotifyStrategy;
    private String timeout;
    private String delayTime;
    private String environmentCode;
    @Data
    public static class TaskParams {
        private Integer customConfig;
        private Object json;
        private Object localParams;
        private Integer xms;
        private Integer xmx;
        private Object dependence;
        private Object conditionResult;
        private Object waitStartTimeout;
        private Object switchResult;
    }

    public String taskDefinitionJson(long taskCode,
                                     String dataxJson,
                                     String environmentCode) {
        this.getTaskParams().setJson(dataxJson);
        this.setCode(taskCode);
        this.setEnvironmentCode(environmentCode);
        return new Gson().toJson(List.of(this));
    }
    public String taskDefinitionJson(long taskCode,
                                     String environmentCode) {
        this.setCode(taskCode);
        this.setEnvironmentCode(environmentCode);
        return new Gson().toJson(List.of(this));
    }
}
