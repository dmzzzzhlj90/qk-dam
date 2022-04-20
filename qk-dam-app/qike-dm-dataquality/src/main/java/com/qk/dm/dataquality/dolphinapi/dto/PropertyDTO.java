package com.qk.dm.dataquality.dolphinapi.dto;

import com.qk.dm.dataquality.dolphinapi.constant.DataType;
import com.qk.dm.dataquality.dolphinapi.constant.Direct;
import lombok.Data;

/**
 * 流程全局参数信息
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
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