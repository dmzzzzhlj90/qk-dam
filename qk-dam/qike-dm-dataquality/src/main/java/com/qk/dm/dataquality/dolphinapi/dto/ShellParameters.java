package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 工作流实例_SHELL执行参数
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class ShellParameters {

    private List<ResourceDTO> resourceList;
    private List<String> localParams;
    private String rawScript;



}