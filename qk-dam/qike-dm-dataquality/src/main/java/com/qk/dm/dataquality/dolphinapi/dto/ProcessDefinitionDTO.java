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

    private int id;

    private String name;

    private int version;

    private String releaseState;

    private int projectId;

    private String processDefinitionJson;

    private String description;

    private String globalParams;

    private List<String> globalParamList;

    private Map<String, String> globalParamMap;

    private Date createTime;

    private Date updateTime;

    private String flag;

    private int userId;

    private String userName;

    private String projectName;

    private String locations;

    private String connects;

    private String receivers;

    private String receiversCc;

    private String scheduleReleaseState;

    private int timeout;

    private int tenantId;

    private String modifyBy;

    private String resourceIds;

}