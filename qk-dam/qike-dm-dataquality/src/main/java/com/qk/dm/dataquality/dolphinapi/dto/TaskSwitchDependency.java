package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/20 7:24 下午
 * @since 1.0.0
 */
@Data
public class TaskSwitchDependency {
    private String localParams;
    private String varPool;
    private String dependRelation;
    private String relation;
    private String nextNode;
    private int resultConditionLocation;
    private String dependTaskList;
    private List<String> resourceFilesList;
    private String varPoolMap;
    private String localParametersMap;
}
