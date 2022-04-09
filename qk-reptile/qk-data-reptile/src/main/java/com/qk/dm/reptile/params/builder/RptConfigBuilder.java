package com.qk.dm.reptile.params.builder;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RptConfigBuilder {
    private String request;
    private Map<String,Object> headers;
    private Map<String,Object> cookies;
    private Map<String,Object> form_data;
    private Map<String,Object> form_urlencoded;
    private Map<String,Object> raw;
    private Integer ip_start;
    private Integer js_start;
    private Map<String, RptSelectorBuilder> columns;
}
