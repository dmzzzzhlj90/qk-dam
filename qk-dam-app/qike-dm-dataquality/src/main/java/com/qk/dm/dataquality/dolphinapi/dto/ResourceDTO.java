package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作流实例_资源列表信息
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class ResourceDTO {

    /**
     * resource id
     */
    private Integer id;

    /**
     * resource id
     */
    private String name;

    /**
     * resource id
     */
    private String res;

}