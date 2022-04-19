package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/20 7:23 下午
 * @since 1.0.0
 */
@Data
public class TaskDependency {
    private String localParams;
    private String varPool;
    private String dependTaskList;
    private String relation;
    private List<String> resourceFilesList;
    private String varPoolMap;
    private String localParametersMap;
}
