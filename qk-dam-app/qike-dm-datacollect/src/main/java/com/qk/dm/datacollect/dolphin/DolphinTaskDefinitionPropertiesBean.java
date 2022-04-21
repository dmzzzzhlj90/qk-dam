package com.qk.dm.datacollect.dolphin;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author zhudaoming
 */
@Component
@Data
@ConfigurationProperties("dolphinscheduler.task")
public class DolphinTaskDefinitionPropertiesBean {
    private Long code;
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
        private Object httpParams;
        private String url;
        private String httpMethod;
        private String httpCheckCondition;
        private String condition;
        private Integer connectTimeout;
        private Integer socketTimeout;
        //
        private Object localParams;
        private Object dependence;
        private Object conditionResult;
        private Object waitStartTimeout;
        private Object switchResult;
    }

    public String taskDefinitionJson(long taskCode,String url,Object httpParams,String httpMethod,DolphinTaskDefinitionPropertiesBean taskParam) {
        this.getTaskParams().setHttpParams(httpParams);
        this.getTaskParams().setUrl(url);
        this.getTaskParams().setHttpMethod(httpMethod);
        this.setCode(taskCode);
        BeanMap abean = BeanMap.create(this);
        BeanMap bbean = BeanMap.create(taskParam);
        abean.forEach((k,v)->{
            if (Objects.nonNull(bbean.get(k))){
                Object tv = bbean.get(k);
                if (tv instanceof TaskParams){
                    BeanMap taskBean = BeanMap.create(abean.get(k));
                    BeanMap targetTaskBean = BeanMap.create(tv);
                    taskBean.forEach((kk,vv)->{
                        taskBean.put(kk,Objects.nonNull(targetTaskBean.get(kk))?targetTaskBean.get(kk):vv);
                    });

                }else{
                    abean.put(k,tv);
                }

            }
        });
        return new Gson().toJson(List.of(abean));
    }

    public String taskDefinitionJson(long taskCode,String url,Object httpParams,String httpMethod) {
        this.setCode(taskCode);
        this.getTaskParams().setHttpParams(httpParams);
        this.getTaskParams().setUrl(url);
        this.getTaskParams().setHttpMethod(httpMethod);
        return new Gson().toJson(List.of(this));
    }
}
