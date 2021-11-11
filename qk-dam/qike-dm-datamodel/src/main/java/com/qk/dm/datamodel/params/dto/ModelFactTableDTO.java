package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelFactTableDTO implements Serializable {

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
     * 维度名称
     */
    private String factName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 0待审核 1已发布2 已下线
     */
    private Integer status;

    /**
     * 连接类型
     */
    private Integer connectionType;

    /**
     * 数据连接
     */
    private String dataConnection;

    /**
     * 数据库名称
     */
    private String databaseName;



}
