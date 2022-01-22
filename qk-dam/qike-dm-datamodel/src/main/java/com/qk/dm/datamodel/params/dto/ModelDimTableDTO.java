package com.qk.dm.datamodel.params.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDimTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 主题id
     */
    private Long themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 维度名称
     */
    private String dimName;


    /**
     * 1 普通维度 2 码表维度 3 层级维度
     */
    private String dimType;

    /**
     * 描述
     */
    private String description;

    /**
     * 0待审核 1已发布2 已下线
     */
    private Integer status;

    /**
     * 连接类型
     */
    private String connectionType;

    /**
     * 数据连接
     */
    private String dataConnection;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 关联维度的id
     */
    private Long modelDimId;

    /**
     * 字段信息
     */
    private List<ModelDimTableColumnDTO> columnList;

}
