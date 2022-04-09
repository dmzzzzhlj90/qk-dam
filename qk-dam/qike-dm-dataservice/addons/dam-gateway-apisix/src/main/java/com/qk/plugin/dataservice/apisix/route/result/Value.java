package com.qk.plugin.dataservice.apisix.route.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Value {

    private String name;

    private String id;

    private String uri;

    private List<String> methods;

    private Map<String, Object> plugins;

    private String upstream_id;

    private int status;

}
