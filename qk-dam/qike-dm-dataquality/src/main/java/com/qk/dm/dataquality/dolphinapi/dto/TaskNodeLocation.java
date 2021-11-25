package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

/**
 * DAG流程图,单独节点,位置信息
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class TaskNodeLocation {
    private String name;
    private String targetarr;
    private String nodenumber;
    private int x;
    private int y;
}