package com.qk.dm.datamodel.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 关系建模基本信息入参类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPhysicalTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称")
    private String tableName;

    /**
     * 主题
     */
    @NotBlank(message = "主题")
    private String theme;

    /**
     * 数据连接
     */
    @NotBlank(message = "数据连接")
    private String dataConnection;

    /**
     * 数据库名称
     */
    @NotBlank(message = "数据库名称")
    private String databaseName;

    /**
     * 描述
     */
    private String description;

    /**
     * 0草稿 1已发布2 已下线
     */
    @NotBlank(message = "状态")
    private Integer status;


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
     * HIVE类型表分为内部表和外部表(1表示内部，2表示外部)
     */
    private Integer tableType ;

    /**
     * 责任人（如果没有输入默认给创建人）
     */
    private String responsibleBy;

    /**
     * HIVE类型表需要选择数据格式
     */
    private String dataFormat;

    /**
     * 所属主题id
     */
    @NotBlank(message = "所属主题id")
    private Long themeId;
    /**
     * 所属层级id
     */
    @NotBlank(message = "所属层级id")
    private Long modelId;

    /**
     * HIVE类型表需要给ftfs路径
     */
    private String hftsRoute;

    /**
     * 数据库和系统定义的sql是否同步，0表示为同步，1表示不同步
     */
    @NotBlank(message = "数据库和系统定义的sql是否同步")
    private Integer syncStatus;
}
