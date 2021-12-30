package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

/**
 * DAG流程图_单独节点_位置信息
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class TaskNodeLocation {

    private Long taskCode;

    private int x;

    private int y;
}