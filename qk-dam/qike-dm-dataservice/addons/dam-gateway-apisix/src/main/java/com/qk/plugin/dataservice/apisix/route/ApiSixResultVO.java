package com.qk.plugin.dataservice.apisix.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qk.plugin.dataservice.apisix.route.result.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装ApiSix接口返回实体对象
 *
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiSixResultVO {

  private int count;
  private String action;
  @JsonIgnore
  private Node node;
}
