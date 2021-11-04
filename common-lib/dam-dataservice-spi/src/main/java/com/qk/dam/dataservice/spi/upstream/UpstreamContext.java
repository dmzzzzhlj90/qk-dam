package com.qk.dam.dataservice.spi.upstream;

import java.util.Map;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpstreamContext {
  private UpstreamInfo upstreamInfo;
  private Map<String, String> params;
}
