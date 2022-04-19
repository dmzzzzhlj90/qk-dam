package com.qk.dam.dataservice.spi.pojo;

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
public class RouteData {

    private String name;

    private String id;

    private String uri;

    private List<String> methods;

    private Map<String, Object> plugins;

    private String upstream_id;

    private String status;

}
