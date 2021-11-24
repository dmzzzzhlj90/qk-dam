package com.qk.dm.dataquality.dolphinapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工作流实例_资源列表信息
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class TenantDTO {

    private int id;

    private String tenantCode;

    private String tenantName;

    private String description;

    private int queueId;

    private String queueName;

    private String queue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}