package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 字段类型校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataTypeCheckVO {

    private DataType source;
    private DataType target;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataType{
        /**
         * 连接类型
         */
        private String connectType;
        /**
         * 字段列表
         */
        private List<ColumnVO.Column> columnList;
    }
}
