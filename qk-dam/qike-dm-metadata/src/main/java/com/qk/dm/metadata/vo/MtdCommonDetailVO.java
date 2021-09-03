package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;


public class MtdCommonDetailVO {
    /** 负责人 */
    private String owner;
    /** 类型名称 */
    private String typeName;
    /** 表行数 */
    private String tableRows;
    /** 数据长度 */
    private String dataLength;
    /** 索引长度 */
    private String indexLength;
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
    /** 标签 */
    private String labels;
    /** 分类 */
    private String classification;
    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /** db信息 */
    private MtdDbInfoVO db;
    /**
     * 实体信息
     */
    private Map<String,Object> relationshipAttributes;


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTableRows() {
        return tableRows;
    }

    public void setTableRows(String tableRows) {
        this.tableRows = tableRows;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(String indexLength) {
        this.indexLength = indexLength;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MtdDbInfoVO getDb() {
        return db;
    }

    public void setDb(MtdDbInfoVO db) {
        this.db = db;
    }

    public Map<String, Object> getRelationshipAttributes() {
        return relationshipAttributes;
    }

    public void setRelationshipAttributes(Map<String, Object> relationshipAttributes) {
        this.relationshipAttributes = relationshipAttributes;
    }
}
