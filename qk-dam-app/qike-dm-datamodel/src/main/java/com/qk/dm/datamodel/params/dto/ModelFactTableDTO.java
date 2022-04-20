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
    private String themeId;

    /**
     * 主题名称
     */
    @NotBlank(message = "主题名称不能为空")
    private String themeName;

    /**
     * 事实表名称
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
    @NotNull(message = "事实表状态不能为空")
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
     * HIVE类型表需要选择数据格式
     */
    private String dataFormat;

    /**
     * HIVE类型表需要给hdfs路径
     */
    private String hdfsRoute ;

    /**
     * HIVE类型表分为内部表和外部表(1表示内部，2表示外部)
     */
    private String tableType;
    /**
     * 责任人名称
     */
    private String responsibleBy;
    /**
     * 字段信息
     */
    private List<ModelFactColumnDTO> columnList;



}
