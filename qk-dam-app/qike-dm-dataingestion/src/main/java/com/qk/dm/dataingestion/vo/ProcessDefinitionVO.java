package com.qk.dm.dataingestion.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProcessDefinitionVO {
    private Integer id;

    private Long code;

    private String name;

    private Integer version;

    private String releaseState;

    private Long projectCode;

    private String description;

    private String globalParams;

    private List<String> globalParamList;

    private Map<String, String> globalParamMap;

    private String createTime;

    private String updateTime;

    private String flag;

    private Integer userId;

    private String userName;

    private String projectName;

    private String locations;

    private String scheduleReleaseState;

    private Integer timeout;

    private Integer tenantId;

    private String tenantCode;

    private String modifyBy;

    private Integer warningGroupId;
}
