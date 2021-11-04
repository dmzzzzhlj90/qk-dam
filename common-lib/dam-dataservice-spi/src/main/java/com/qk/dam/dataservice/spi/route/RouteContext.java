package com.qk.dam.dataservice.spi.route;

import java.util.Map;
import lombok.*;
import lombok.Builder;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteContext {
  private RouteInfo routeInfo;
  private Map<String, String> params;
}
