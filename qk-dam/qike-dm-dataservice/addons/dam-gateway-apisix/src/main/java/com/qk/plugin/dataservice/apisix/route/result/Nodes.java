package com.qk.plugin.dataservice.apisix.route.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nodes {

  private String key;
  private Value value;
}
