package com.qk.dam.dataservice.spi.server;

import lombok.*;

import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerContext {
  private ServerInfo serverInfo;
  private Map<String, String> params;
}
