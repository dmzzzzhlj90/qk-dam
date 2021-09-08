package com.qk.dm.metadata.vo;

import lombok.Builder;

@Builder
public class MtdColumnVO extends MtdAtlasBaseDetailVO{

    /** 是否是主键 */
    private String isPrimaryKey;
    /** 默认值 */
    private String defaultValue;
    /** 表信息 */
    private MtdTableInfoVO table;

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

    public MtdTableInfoVO getTable() {
        return table;
    }

    public void setTable(MtdTableInfoVO table) {
        this.table = table;
    }
}

