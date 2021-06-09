package com.qk.dam.sqlloader.vo;

import java.util.Date;
import java.util.Objects;

public class PiciTaskLogVO {
    private long id;
    private int pici;
    private String tableName;
    /**
     * 是否下载入库：1是，0否
     */
    private int is_down;
    private int is_mysql_updated;
    private int is_es_updated;
    private Date updated;


    public PiciTaskLogVO(int pici, String tableName, int is_down, Date updated) {
        this.pici = pici;
        this.tableName = tableName;
        this.is_down = is_down;
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "PiciTaskLogVO{" +
                "id=" + id +
                ", pici=" + pici +
                ", tableName='" + tableName + '\'' +
                ", is_down=" + is_down +
                ", is_mysql_updated=" + is_mysql_updated +
                ", is_es_updated=" + is_es_updated +
                ", updated=" + updated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PiciTaskLogVO)) return false;
        PiciTaskLogVO that = (PiciTaskLogVO) o;
        return getPici() == that.getPici() && getTableName().equals(that.getTableName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPici(), getTableName());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPici() {
        return pici;
    }

    public void setPici(int pici) {
        this.pici = pici;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getIs_down() {
        return is_down;
    }

    public void setIs_down(int is_down) {
        this.is_down = is_down;
    }

    public int getIs_mysql_updated() {
        return is_mysql_updated;
    }

    public void setIs_mysql_updated(int is_mysql_updated) {
        this.is_mysql_updated = is_mysql_updated;
    }

    public int getIs_es_updated() {
        return is_es_updated;
    }

    public void setIs_es_updated(int is_es_updated) {
        this.is_es_updated = is_es_updated;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
