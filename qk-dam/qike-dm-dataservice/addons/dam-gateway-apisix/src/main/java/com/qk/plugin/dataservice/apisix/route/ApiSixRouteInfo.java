package com.qk.plugin.dataservice.apisix.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qk.dam.dataservice.spi.route.RouteInfo;
import java.util.List;
import java.util.Map;
import lombok.*;

/**
 * 封装ApiSix_Route入参实体对象
 *
 * @author wjq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSixRouteInfo extends RouteInfo {
  private String id;
  private String name;
  private Integer status;
  private List<String> uris; // [\"/das/*\"]
  private List<String>
      methods; // [\"GET\",\"POST\",\"PUT\",\"DELETE\",\"PATCH\",\"HEAD\",\"OPTIONS\",\"CONNECT\",\"TRACE\"]
  //  private List<List<String>> vars; //
  // [[\"arg_code111\",\"==\",\"123\"],[\"arg_value222\",\"==\",\"666\"]]
  //  private Map<String, Map<String, Object>> plugins;
  //  private Upstream upstream;
  private String upstream_id;
  private String service_id;
  private Map<String, String> labels;
  private String desc;
}
