package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder
public class MtdTableDetailVO extends MtdAtlasBaseDetailVO{
    /** 表行数 */
    private String tableRows;
    /** 数据长度 */
    private String dataLength;
    /** 索引长度 */
    private String indexLength;
    /** db信息 */
    private MtdDbInfoVO db;

    /** 参考实体 */
    private List<Map<String, Object>> columns;

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

    public MtdDbInfoVO getDb() {
        return db;
    }

    public void setDb(MtdDbInfoVO db) {
        this.db = db;
    }

    public List<Map<String, Object>> getColumns() {
        return columns;
    }

    public void setColumns(List<Map<String, Object>> columns) {
        this.columns = columns;
    }
}
