package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 新建API_取数SQL方式VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateSqlScriptVO {
    /**
     * API基础信息
     */
    @Valid
    private DasApiBasicInfoVO dasApiBasicInfoVO;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * API基础信息ID
     */
    private String apiId;

    /**
     * 取数据方式
     */
    @NotBlank(message = "取数据方式不能为空！")
    private String accessMethod;

    /**
     * 数据源连接类型
     */
    @NotBlank(message = "数据源类型不能为空！")
    private String connectType;

    /**
     * 数据源连接名称
     */
    @NotBlank(message = "数据源连接不能为空！")
    private String dataSourceName;

    /**
     * 数据库
     */
    @NotBlank(message = "数据库不能为空！")
    private String dataBaseName;

    /**
     * 请求参数
     */
    private List<DasApiCreateRequestParasVO> apiCreateRequestParasVOS;

    /**
     * 响应参数
     */
    private List<DasApiCreateResponseParasVO> apiCreateResponseParasVOS;

    /**
     * 排序参数
     */
    private List<DasApiCreateOrderParasVO> apiCreateOrderParasVOS;

    /**
     * 取数脚本
     */
    @NotBlank(message = "取数脚本不能为空！")
    private String sqlPara;

    /**
     * 描述
     */
    private String description;
}
