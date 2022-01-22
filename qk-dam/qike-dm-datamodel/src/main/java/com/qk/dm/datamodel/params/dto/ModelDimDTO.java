package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ModelDimDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    private Long id;
    /**
     * 主题id
     */

    @NotNull(message = "主题id不能为空 ")
    private Long themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 维度名称
     */
    @NotBlank(message = "维度名称不能为空")
    private String dimName;

    /**
     * 维度编码
     */
    //private String dimCode;

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
     * 责任人
     */
    private String responsibilityPerson;

    /**
     * 维度字段信息
     */
    @NotNull(message = "维度字段不能为空 ")
    private List<ModelDimColumnDTO> columnList;


}
