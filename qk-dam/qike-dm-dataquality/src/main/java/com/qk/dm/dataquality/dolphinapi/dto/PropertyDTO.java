package com.qk.dm.dataquality.dolphinapi.dto;

import com.qk.dm.dataquality.dolphinapi.constant.DataType;
import com.qk.dm.dataquality.dolphinapi.constant.Direct;
import lombok.Data;

@Data
public class PropertyDTO {
    /**
     * key
     */
    private String prop;

    /**
     * input/output
     */
    private Direct direct;

    /**
     * data type
     */
    private DataType type;

    /**
     * value
     */
    private String value;
}