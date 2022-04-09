package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程实例定义对象(列表信息)
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class ProcessDefinitionDTO implements Serializable {

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

//    private Date createTime;
//
//    private Date updateTime;

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