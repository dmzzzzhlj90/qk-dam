package com.qk.dam.dataservice.spi.consunmer;

import java.util.Map;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerContext {
  private ConsumerInfo consumerInfo;
  private Map<String, String> params;
}
