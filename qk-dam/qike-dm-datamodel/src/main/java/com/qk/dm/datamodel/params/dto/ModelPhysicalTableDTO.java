package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ModelPhysicalTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pagination pagination;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 主题
     */
    private String theme;

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
    private String desc;

    /**
     * 0草稿 1已发布2 已下线
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


}
