package com.qk.dm.metadata.lineage;

import org.apache.atlas.model.instance.AtlasObjectId;
import org.apache.atlas.type.AtlasTypeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 处理过程配置
 *
 * @author zhudaoming
 */
@Configuration
public class ProcessConfig {
    public final String UTILITY_CLASS = "Utility class";

    @Bean
    public Process processIds() {
        return new Process() {
            @Override
            public Collection<AtlasObjectId> inputIds(String typeName, String[] qualifiedName) {
                return Arrays.stream(qualifiedName)
                        .map(q -> new AtlasObjectId(typeName, Map.of(AtlasTypeUtil.ATTRIBUTE_QUALIFIED_NAME, q)))
                        .collect(Collectors.toList());
            }

            @Override
            public Collection<AtlasObjectId> outputIds(String typeName, String[] qualifiedName) {
                return Arrays.stream(qualifiedName)
                        .map(q -> new AtlasObjectId(typeName, Map.of(AtlasTypeUtil.ATTRIBUTE_QUALIFIED_NAME, q)))
                        .collect(Collectors.toList());
            }
        };
    }


}
