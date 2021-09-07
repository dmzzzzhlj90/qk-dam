package com.qk.dam.dataservice.spi.consunmer;

import lombok.*;

import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerContext {
    private ConsumerInfo consumerInfo;
    private Map<String, String> params;
}
