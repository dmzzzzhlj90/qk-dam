package com.qk.dm.dataservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "openapi", ignoreInvalidFields = true)
@Component(value = "openApiConnectInfo")
public class OpenApiConnectInfo {

  private String url;

  //  private String host;
  //
  //  private String protocolType;
}
