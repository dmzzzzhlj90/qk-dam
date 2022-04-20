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
public class ProxyRewrite {
  // {\"ProxyRewrite\":{\"regex_uri\":[\"^/das/(.*)\",\"/$1\"]}}
  private List<String> regex_uri;
}
