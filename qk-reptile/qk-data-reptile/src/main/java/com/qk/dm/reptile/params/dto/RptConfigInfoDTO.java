package com.qk.dm.reptile.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 配置信息
 * @author wangzp
 * @date 2021/12/10 14:20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptConfigInfoDTO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 基础信息表id
     */
    @NotNull(message = "基础信息表id不能为空")
    private Long baseInfoId;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空")
    private Long parentId;

    /**
     * raw参数
     */
    private String raw;

    /**
     * x-www-form-urlencoded参数
     */
    private String formUrlencoded;

    /**
     * from-data参数
     */
    private String fromData;

    /**
     * cookies参数
     */
    private String cookies;

    /**
     * headers参数
     */
    private String headers;

    /**
     * 维度目录id
     */
    private Long dimensionId;

    /**
     * 维度目录名称
     */
    private String dimensionName;

    /**
     * 是否启动动态加载js(0表示未启动、1表示启动)
     */
    private Integer startoverJs;

    /**
     * 是否启动代理IP(0表示未启动、1表示启动)
     */
    private Integer startoverIp;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方式
     */
    private String requestType;
    /**
     * 选择器列表
     */
    private List<RptSelectorColumnInfoDTO> selectorList;

}
