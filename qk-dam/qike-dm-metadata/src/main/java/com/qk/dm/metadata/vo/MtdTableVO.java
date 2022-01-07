package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdTableVO {

    private String owner;
    private String createTime;
    private String qualifiedName;
    private String name;
    private String description;
    private String comment;
    private String guid;
    private String typeName;
    private String labels;
    private String classification;
}
