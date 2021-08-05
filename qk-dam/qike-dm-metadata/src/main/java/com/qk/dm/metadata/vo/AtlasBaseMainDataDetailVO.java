package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AtlasBaseMainDataDetailVO {

    /**
     * 负责人
     */
    private String owner;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 表行数
     */
    private String tableRows;
    /**
     * 数据长度
     */
    private String dataLength;
    /**
     * 索引长度
     */
    private String indexLength;
    /**
     * 备注信息
     */
    private String comment;
    /**
     * 限定名
     */
    private String qualifiedName;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 标签
     */
    private String labels;
    /**
     * 分类
     */
    private String classification;
    /**
     *列信息
     */
    private List<Map<String, Object>> columnList;
}