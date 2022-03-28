package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ModelFactTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 主题id
     */
    private Long themeId;

    /**
     * 主题名称
     */
    @NotBlank(message = "主题名称不能为空")
    private String themeName;

    /**
     * 维度表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String factName;

    /**
     * 描述
     */
    private String description;

    /**
     * 0待审核 1已发布2 已下线
     */
    @NotNull(message = "维度表状态不能为空")
    private Integer status;

    /**
     * 数据源连接名称
     */
    private String dataSourceName;

    /**
     * 数据连接
     */
    private String dataConnection;

    /**
     * 数据库名称
     */
    private String dataBaseName;
    /**
     * 责任人名称
     */
    private String responsibleBy;
    /**
     * 字段信息
     */
    private List<ModelFactColumnDTO> columnList;



}
