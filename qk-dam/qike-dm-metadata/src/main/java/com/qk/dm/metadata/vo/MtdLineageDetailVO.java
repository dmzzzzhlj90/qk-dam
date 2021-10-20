package com.qk.dm.metadata.vo;

import lombok.Data;

@Data
public class MtdLineageDetailVO {
    private String guid;
    private String displayText;
    private String typeName;
    private String status;
    private String[] labels;
    private boolean isIncomplete;

}
