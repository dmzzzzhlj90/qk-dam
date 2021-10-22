package com.qk.dm.metadata.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RelationVO {
    private RelationCommonVO db;
    private List<RelationCommonVO> tables;
    private List<RelationCommonVO> columns;
    private RelationCommonVO table;
    private List<Map<String,Object>> outputs;
    private List<Map<String,Object>> inputs;
}
