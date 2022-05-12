package com.dolphinscheduler.shell;

import com.google.gson.Gson;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author shenpengjie
 */
@Component
@ConfigurationProperties("dolphinscheduler.task")
public class DolphinTaskDefinitionPropertiesBean {
    private Long code;
    private String name;
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
    private Long projectCode;
    private Integer version;

    public static class TaskParams {
        private Object resourceList;
        private String rawScript;
        private String rawScriptInit;
        private Object localParams;
        private Object dependence;
        private Object conditionResult;
        private Object waitStartTimeout;
        private Object switchResult;

        public Object getResourceList() {
            return resourceList;
        }

        public void setResourceList(Object resourceList) {
            this.resourceList = resourceList;
        }

        public String getRawScript() {
            return rawScript;
        }

        public void setRawScript(String rawScript) {
            this.rawScript = rawScript;
        }

        public String getRawScriptInit() {
            return rawScriptInit;
        }

        public void setRawScriptInit(String rawScriptInit) {
            this.rawScriptInit = rawScriptInit;
        }

        public Object getLocalParams() {
            return localParams;
        }

        public void setLocalParams(Object localParams) {
            this.localParams = localParams;
        }

        public Object getDependence() {
            return dependence;
        }

        public void setDependence(Object dependence) {
            this.dependence = dependence;
        }

        public Object getConditionResult() {
            return conditionResult;
        }

        public void setConditionResult(Object conditionResult) {
            this.conditionResult = conditionResult;
        }

        public Object getWaitStartTimeout() {
            return waitStartTimeout;
        }

        public void setWaitStartTimeout(Object waitStartTimeout) {
            this.waitStartTimeout = waitStartTimeout;
        }

        public Object getSwitchResult() {
            return switchResult;
        }

        public void setSwitchResult(Object switchResult) {
            this.switchResult = switchResult;
        }

    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public TaskParams getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(TaskParams taskParams) {
        this.taskParams = taskParams;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(String workerGroup) {
        this.workerGroup = workerGroup;
    }

    public String getFailRetryTimes() {
        return failRetryTimes;
    }

    public void setFailRetryTimes(String failRetryTimes) {
        this.failRetryTimes = failRetryTimes;
    }

    public String getFailRetryInterval() {
        return failRetryInterval;
    }

    public void setFailRetryInterval(String failRetryInterval) {
        this.failRetryInterval = failRetryInterval;
    }

    public String getTimeoutFlag() {
        return timeoutFlag;
    }

    public void setTimeoutFlag(String timeoutFlag) {
        this.timeoutFlag = timeoutFlag;
    }

    public String getTimeoutNotifyStrategy() {
        return timeoutNotifyStrategy;
    }

    public void setTimeoutNotifyStrategy(String timeoutNotifyStrategy) {
        this.timeoutNotifyStrategy = timeoutNotifyStrategy;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getEnvironmentCode() {
        return environmentCode;
    }

    public void setEnvironmentCode(String environmentCode) {
        this.environmentCode = environmentCode;
    }

    public Long getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Long projectCode) {
        this.projectCode = projectCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String taskDefinitionJson(long taskCode, Object params, String rawScript, DolphinTaskDefinitionPropertiesBean taskParam) {
        this.setCode(taskCode);
        this.getTaskParams().setRawScript(this.getTaskParams().getRawScriptInit() + rawScript);
        this.getTaskParams().setLocalParams(params);
        this.setVersion(1);
        if (taskParam != null) {
            //转变成map没有顺序
            BeanMap abean = BeanMap.create(this);
            BeanMap bbean = BeanMap.create(taskParam);
            abean.forEach((k, v) -> {
                if (Objects.nonNull(bbean.get(k))) {
                    Object tv = bbean.get(k);
                    if (tv instanceof TaskParams) {
                        BeanMap taskBean = BeanMap.create(abean.get(k));
                        BeanMap targetTaskBean = BeanMap.create(tv);
                        taskBean.forEach((kk, vv) -> {
                            taskBean.put(kk, Objects.nonNull(targetTaskBean.get(kk)) ? targetTaskBean.get(kk) : vv);
                        });
                    } else {
                        abean.put(k, tv);
                    }
                }
            });
            return new Gson().toJson(List.of(abean));
        } else {
            return new Gson().toJson(List.of(this));
        }
    }

    public String taskDefinitionJson(long taskCode, Object params, String rawScript) {
        this.setCode(taskCode);
        this.getTaskParams().setRawScript(this.getTaskParams().getRawScriptInit() + rawScript);
        this.getTaskParams().setLocalParams(params);
        return new Gson().toJson(List.of(this));
    }
}
