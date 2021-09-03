package com.qk.plugin.dataservice.apisix.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qk.dam.dataservice.spi.consunmer.ConsumerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 封装ApiSix入参实体对象
 *
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSixConsumerInfo extends ConsumerInfo {
    private String username;
    private Map<String, Map<String, String>> plugins;
}