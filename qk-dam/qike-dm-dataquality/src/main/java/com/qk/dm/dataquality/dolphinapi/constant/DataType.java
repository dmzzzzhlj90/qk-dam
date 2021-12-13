package com.qk.dm.dataquality.dolphinapi.constant;

/**
 * 流程全局参数信息_数据类型
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
public enum DataType {
    /**
     * 0 string
     * 1 integer
     * 2 long
     * 3 float
     * 4 double
     * 5 date, "YYYY-MM-DD"
     * 6 time, "HH:MM:SS"
     * 7 time stamp
     * 8 Boolean
     */
    VARCHAR,INTEGER,LONG,FLOAT,DOUBLE,DATE,TIME,TIMESTAMP,BOOLEAN
}
