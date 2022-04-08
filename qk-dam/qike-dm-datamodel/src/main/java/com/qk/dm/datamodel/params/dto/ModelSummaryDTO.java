package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ModelSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pagination pagination;

    private Long id;

    /**
     * 主题id
     */
    private String themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 表名称
     */
    @NotNull(message = "表名称不能为空")
    private String tableName;

    /**
     * 维度id
     */
    private Long dimId;

    /**
     * 维度名称
     */
    private String dimName;
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
     * 描述
     */
    private String description;

    private List<ModelSummaryIdcDTO> modelSummaryIdcList;


}
