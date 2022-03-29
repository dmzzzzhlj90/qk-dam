package com.qk.dm.metadata.lineage;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author zhudaoming
 */
@Data
public class LineageTemplateVO {
    @ExcelProperty(index = 0)
    private String sourceConnectName;
    @ExcelProperty(index = 1)
    private String sourceEntity;
    @ExcelProperty(index = 2)
    private String processId;
    @ExcelProperty(index = 3)
    private String targetConnectName;
    @ExcelProperty(index = 4)
    private String targetEntity;
    @ExcelProperty(index = 5)
    private String user;
    @ExcelProperty(index = 6)
    private String description;
    private Long time;

}
