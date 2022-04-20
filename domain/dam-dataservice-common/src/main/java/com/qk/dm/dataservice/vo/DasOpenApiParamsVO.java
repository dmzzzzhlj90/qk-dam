package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * OpenApi参数信息VO
 *
 * @author wjq
 * @date 20220301
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasOpenApiParamsVO {

    /**
     * API目录ID
     */
    @NotBlank(message = "API目录ID不能为空！")
    private String dirId;

    /**
     * OpenApi路径
     */
    @NotBlank(message = "OpenApi路径不能为空！")
    private List<String> openApiPaths;

    /**
     * 同步方式
     */
    @NotBlank(message = "同步方式路径不能为空！")
    private String apiSyncType;

}
