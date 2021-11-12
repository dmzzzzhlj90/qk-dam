package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pagination pagination;

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
     * 表名称
     */
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
     * 数据连接
     */
    private String dataConnection;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 描述
     */
    private String description;


}
