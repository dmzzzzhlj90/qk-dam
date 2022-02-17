package com.qk.dm.reptile.params.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptSelectorBuilder {
    private Integer method;
    private String val;
    private Integer num;
    private String before;
    private String after;
}
