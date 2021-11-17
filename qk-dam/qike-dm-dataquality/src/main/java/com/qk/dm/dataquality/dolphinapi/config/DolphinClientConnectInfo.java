package com.qk.dm.dataquality.dolphinapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 调度引擎Dolphin Scheduler Client配置信息
 *
 * @author wjq
 * @date 2021/11/17
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "dolphin", ignoreInvalidFields = true)
@Component(value = "dolphinClientConnectInfo")
public class DolphinClientConnectInfo {

    private String defaultBaseUrl;

    private String tokenKey;

    private String tokenValue;

}
