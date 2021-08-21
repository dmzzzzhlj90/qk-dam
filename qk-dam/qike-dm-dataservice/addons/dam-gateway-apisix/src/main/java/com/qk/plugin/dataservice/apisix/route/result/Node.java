package com.qk.plugin.dataservice.apisix.route.result;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node {

  private List<Nodes> nodes;
  private String key;
  private boolean dir;
}
