package com.qk.dam.dataservice.spi.server;

import java.util.Map;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerContext {
  private ServerInfo serverInfo;
  private Map<String, String> params;
}
