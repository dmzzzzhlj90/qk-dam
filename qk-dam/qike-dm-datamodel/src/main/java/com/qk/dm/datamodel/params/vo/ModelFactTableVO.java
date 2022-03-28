package com.qk.dm.datamodel.params.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ModelFactTableVO implements Serializable {

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
     * 维度编码
     */
    private String dimCode;

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
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 责任人名称
     */
    private String responsibleBy;

    /**
     * 字段信息
     */
    private List<ModelFactColumnVO> columnList;

}