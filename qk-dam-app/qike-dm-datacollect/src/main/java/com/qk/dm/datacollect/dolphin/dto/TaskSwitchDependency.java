package com.qk.dm.datacollect.dolphin.dto;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/20 7:24 下午
 * @since 1.0.0
 */
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

    public String getDependRelation() {
        return dependRelation;
    }

    public void setDependRelation(String dependRelation) {
        this.dependRelation = dependRelation;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public int getResultConditionLocation() {
        return resultConditionLocation;
    }

    public void setResultConditionLocation(int resultConditionLocation) {
        this.resultConditionLocation = resultConditionLocation;
    }

    public String getDependTaskList() {
        return dependTaskList;
    }

    public void setDependTaskList(String dependTaskList) {
        this.dependTaskList = dependTaskList;
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
