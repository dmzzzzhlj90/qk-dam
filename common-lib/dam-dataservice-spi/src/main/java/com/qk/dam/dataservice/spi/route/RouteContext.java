package com.qk.dam.dataservice.spi.route;

import lombok.*;

import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteContext {
    private RouteInfo routeInfo;
    private Map<String, String> params;
}
