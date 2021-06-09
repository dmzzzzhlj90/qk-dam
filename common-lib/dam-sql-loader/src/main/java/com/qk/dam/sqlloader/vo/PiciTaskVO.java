package com.qk.dam.sqlloader.vo;

public class PiciTaskVO {
    private int pici;
    private String tableName;
    private String ossPath;

    public PiciTaskVO(int pici, String tableName, String ossPath) {
        this.pici = pici;
        this.tableName = tableName;
        this.ossPath = ossPath;
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

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    @Override
    public String toString() {
        return "PiciTaskVO{" +
                "pici=" + pici +
                ", tableName='" + tableName + '\'' +
                ", ossPath='" + ossPath + '\'' +
                '}';
    }
}
