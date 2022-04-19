package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnVO {

    private List<Column> sourceColumnList;
    private List<Column> targetColumnList;

    @Data
    @Builder
    public static class Column{
        private String name;
        private String dataType;
    }
}
