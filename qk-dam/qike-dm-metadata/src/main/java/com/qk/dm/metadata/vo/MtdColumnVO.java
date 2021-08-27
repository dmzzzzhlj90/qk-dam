package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MtdColumnVO {

    /** 负责人 */
    private String owner;
    /** 类型名称 */
    private String typeName;
    /** 展示名称*/
    private String displayName;
    /** 备注信息 */
    private String comment;
    /** 限定名 */
    private String qualifiedName;
    /** 描述信息 */
    private String description;
    /**数据类型 */
    private String dataType;
    /** 是否是主键 */
    private String isPrimaryKey;
    /** 默认值 */
    private String defaultValue;
    /** 创建时间 */
    private String createTime;
    /** 标签 */
    private String labels;
    /** 分类 */
    private String classification;
    /** 表信息 */
    private MtdTableInfoVO table;
}

