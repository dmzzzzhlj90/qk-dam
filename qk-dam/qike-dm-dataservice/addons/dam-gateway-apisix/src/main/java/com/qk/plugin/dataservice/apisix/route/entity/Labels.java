package com.qk.plugin.dataservice.apisix.route.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Labels {
  // {\"API_VERSION\":\"1.0\"}
  private String API_VERSION;
}
