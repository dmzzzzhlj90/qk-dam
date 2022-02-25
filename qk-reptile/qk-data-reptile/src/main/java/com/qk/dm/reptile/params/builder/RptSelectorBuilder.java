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
    private String request_before;
    private String request_after;
    private String source_before;
    private String source_after;
}
