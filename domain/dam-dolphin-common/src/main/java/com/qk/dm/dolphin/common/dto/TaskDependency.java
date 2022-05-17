package com.qk.dm.dolphin.common.dto;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/20 7:23 下午
 * @since 1.0.0
 */

public class TaskDependency {
    private String localParams;
    private String varPool;
    private String dependTaskList;
    private String relation;
    private List<String> resourceFilesList;
    private String varPoolMap;
    private String localParametersMap;

    public String getLocalParams() {
        return localParams;
    }

    public void setLocalParams(String localParams) {
        this.localParams = localParams;
    }

    public String getVarPool() {
        return varPool;
    }

    public void setVarPool(String varPool) {
        this.varPool = varPool;
    }

    public String getDependTaskList() {
        return dependTaskList;
    }

    public void setDependTaskList(String dependTaskList) {
        this.dependTaskList = dependTaskList;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public List<String> getResourceFilesList() {
        return resourceFilesList;
    }

    public void setResourceFilesList(List<String> resourceFilesList) {
        this.resourceFilesList = resourceFilesList;
    }

    public String getVarPoolMap() {
        return varPoolMap;
    }

    public void setVarPoolMap(String varPoolMap) {
        this.varPoolMap = varPoolMap;
    }

    public String getLocalParametersMap() {
        return localParametersMap;
    }

    public void setLocalParametersMap(String localParametersMap) {
        this.localParametersMap = localParametersMap;
    }
}
