package com.qk.dm.datamodel.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 事实表名称
     */
    private String factName;

    /**
     * 维度编码
     */
    private String dimCode;

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
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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