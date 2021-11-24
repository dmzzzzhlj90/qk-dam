package com.qk.dm.dataquality.dolphinapi.builder;

import lombok.Data;


/**
 * @author shenpengjie
 */
@Data
public class ProcessData {
    private Integer id;
    private String name;
    private Integer version;
    private String releaseState;
    private Integer projectId;
    private Integer userId;
    private String userName;
    private String projectName;
    private Integer tenantId;
    private String modifyBy;
}
