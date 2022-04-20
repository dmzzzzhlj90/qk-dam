package com.qk.dm.datamodel.params.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelSummaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

}
