package com.qk.plugin.dataservice.apisix.route.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Upstream {
  /**
   * {\"nodes\":[{\"weight\":1,\"host\":\"127.0.0.1\",\"port\":8786}],
   * \"timeout\":{\"send\":30,\"connect\":30,\"read\":30}, \"scheme\":\"http\",
   * \"type\":\"roundrobin\", \"pass_host\":\"node\"}
   */
  private List<Nodes> nodes;

  private Timeout timeout;
  private String scheme;
  private String type;
  private String pass_host;
}
