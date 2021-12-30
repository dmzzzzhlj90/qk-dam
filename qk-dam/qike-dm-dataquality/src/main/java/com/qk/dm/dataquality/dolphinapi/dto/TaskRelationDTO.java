package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.util.Map;

/**
 * 流程定义节点关联信息
 *
 * @author wjq
 * @date 2021/12/21
 * @since 1.0.0
 */
@Data
public class TaskRelationDTO {

    private String name;

    private Integer preTaskCode;

    private Integer preTaskVersion;

    private Long postTaskCode;

    private Integer postTaskVersion;

    private Integer conditionType;

    private Map<String, Object> conditionParams;

}
