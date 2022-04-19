package com.qk.plugin.dataservice.apisix.route.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 插件设置 limit-count
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PluginLimitCount {

    private int count;

    private String key_type;

    private String key;

    private String policy;

    private int time_window;

    private int rejected_code;

}