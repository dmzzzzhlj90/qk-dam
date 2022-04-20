package com.qk.dm.metadata.lineage;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author zhudaoming
 */
@Data
public class DataConnect {
    @ExcelProperty(index = 0)
    private String name;
    @ExcelProperty(index = 1)
    private String type;
    @ExcelProperty(index = 2)
    private String host;
    @ExcelProperty(index = 3)
    private String atlasType;

}