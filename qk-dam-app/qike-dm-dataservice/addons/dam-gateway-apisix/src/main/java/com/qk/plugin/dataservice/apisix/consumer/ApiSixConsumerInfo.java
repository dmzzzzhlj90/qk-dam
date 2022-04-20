package com.qk.plugin.dataservice.apisix.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qk.dam.dataservice.spi.consunmer.ConsumerInfo;
import java.util.Map;
import lombok.*;

/**
 * 封装ApiSix入参实体对象
 *
 * @author wjq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSixConsumerInfo extends ConsumerInfo {
  private String username;
  private Map<String, Map<String, String>> plugins;
}
