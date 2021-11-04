package com.qk.plugin.dataservice.apisix.route.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nodes {
  private Integer weight;
  private String host;
  private Integer port;
}
