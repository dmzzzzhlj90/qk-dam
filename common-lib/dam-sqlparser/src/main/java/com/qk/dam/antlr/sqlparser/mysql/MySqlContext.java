package com.qk.dam.antlr.sqlparser.mysql;


import java.util.ArrayList;
import java.util.List;

public class MySqlContext {

    /**
     * 表名
     */
    public String tableName;

    /**
     * 插入条数
     */
    public Integer insertRows;

    /**
     * where条件
     */
    private String whereCondition;

    /**
     * 查询列名集合
     */
    public List<SQL> queryColumnNames = new ArrayList<>();

    /**
     * where 查询列名条件集合
     */
    public List<SQL> queryWhereColumnNames = new ArrayList<>();

    /**
     * where 查询列名对应值集合
     */
    public List<SQL> queryWhereValColumnNames = new ArrayList<>();

    /**
     * 查询列名集合
     */
    public List<SQL> insertColumnNames = new ArrayList<>();

    /**
     * 插入列表对象值集合
     */
    public List<List<String>> insertForValColumnNames = new ArrayList<>();

    /**
     * 删除条件列表集合
     */
    
    public List<SQL> deleteForWhereColumnNames = new ArrayList<>();

    /**
     * 删除列表对象值集合
     */
    
    public List<SQL> deleteForWhereValColumnNames = new ArrayList<>();

    /**
     * 更新条件列表集合
     */
    
    public List<SQL> updateForWhereColumnNames = new ArrayList<>();

    /**
     * 更新列表对象值集合
     */
    
    public List<SQL> updateForWhereValColumnNames = new ArrayList<>();

    /**
     * 更新列表对象值集合
     */
    
    public List<SQL> updateFoColumnNames = new ArrayList<>();


    /**
     * 更新列表对象值集合
     */
    
    public List<SQL> updateForValues = new ArrayList<>();

    public void addForInsertColumnName(String columnName) {
        SQL sql = new SQL();
        sql.setInsertColumnName(columnName);
        insertColumnNames.add(sql);
    }

    public void addForInsertValColumnName(List<String> columnName) {
        insertForValColumnNames.add(columnName);
    }

    public void addQueryColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setColumnName(columnName);
        queryColumnNames.add(sql);
    }

    public void addQueryWhereColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setQueryWhereColumnName(columnName);
        queryWhereColumnNames.add(sql);
    }

    public void addQueryWhereValColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setQueryWhereValColumnName(columnName);
        queryWhereValColumnNames.add(sql);
    }

    public void addDeleteWhereColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setDeleteWhereColumnName(columnName);
        deleteForWhereColumnNames.add(sql);
    }

    public void addDeleteWhereValColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setDeleteWhereValColumnName(columnName);
        deleteForWhereValColumnNames.add(sql);
    }


    public void addUpdateWhereValColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setUpdateWhereValColumnName(columnName);
        updateForWhereValColumnNames.add(sql);
    }


    public void addUpdateWhereColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setUpdateWhereColumnName(columnName);
        updateForWhereColumnNames.add(sql);
    }

    public void addUpdateColumnNames(String columnName) {
        SQL sql = new SQL();
        sql.setUpdateColumn(columnName);
        updateFoColumnNames.add(sql);
    }

    public void addUpdateValues(String columnName) {
        SQL sql = new SQL();
        sql.setUpdateValue(columnName);
        updateForValues.add(sql);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getInsertRows() {
        return insertRows;
    }

    public void setInsertRows(Integer insertRows) {
        this.insertRows = insertRows;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public List<SQL> getQueryColumnNames() {
        return queryColumnNames;
    }

    public void setQueryColumnNames(List<SQL> queryColumnNames) {
        this.queryColumnNames = queryColumnNames;
    }

    public List<SQL> getQueryWhereColumnNames() {
        return queryWhereColumnNames;
    }

    public void setQueryWhereColumnNames(List<SQL> queryWhereColumnNames) {
        this.queryWhereColumnNames = queryWhereColumnNames;
    }

    public List<SQL> getQueryWhereValColumnNames() {
        return queryWhereValColumnNames;
    }

    public void setQueryWhereValColumnNames(List<SQL> queryWhereValColumnNames) {
        this.queryWhereValColumnNames = queryWhereValColumnNames;
    }

    public List<SQL> getInsertColumnNames() {
        return insertColumnNames;
    }

    public void setInsertColumnNames(List<SQL> insertColumnNames) {
        this.insertColumnNames = insertColumnNames;
    }

    public List<List<String>> getInsertForValColumnNames() {
        return insertForValColumnNames;
    }

    public void setInsertForValColumnNames(List<List<String>> insertForValColumnNames) {
        this.insertForValColumnNames = insertForValColumnNames;
    }

    public List<SQL> getDeleteForWhereColumnNames() {
        return deleteForWhereColumnNames;
    }

    public void setDeleteForWhereColumnNames(List<SQL> deleteForWhereColumnNames) {
        this.deleteForWhereColumnNames = deleteForWhereColumnNames;
    }

    public List<SQL> getDeleteForWhereValColumnNames() {
        return deleteForWhereValColumnNames;
    }

    public void setDeleteForWhereValColumnNames(List<SQL> deleteForWhereValColumnNames) {
        this.deleteForWhereValColumnNames = deleteForWhereValColumnNames;
    }

    public List<SQL> getUpdateForWhereColumnNames() {
        return updateForWhereColumnNames;
    }

    public void setUpdateForWhereColumnNames(List<SQL> updateForWhereColumnNames) {
        this.updateForWhereColumnNames = updateForWhereColumnNames;
    }

    public List<SQL> getUpdateForWhereValColumnNames() {
        return updateForWhereValColumnNames;
    }

    public void setUpdateForWhereValColumnNames(List<SQL> updateForWhereValColumnNames) {
        this.updateForWhereValColumnNames = updateForWhereValColumnNames;
    }

    public List<SQL> getUpdateFoColumnNames() {
        return updateFoColumnNames;
    }

    public void setUpdateFoColumnNames(List<SQL> updateFoColumnNames) {
        this.updateFoColumnNames = updateFoColumnNames;
    }

    public List<SQL> getUpdateForValues() {
        return updateForValues;
    }

    public void setUpdateForValues(List<SQL> updateForValues) {
        this.updateForValues = updateForValues;
    }

    public static class SQL {
        private String columnName;
        private String tableName;
        private String queryWhereValColumnName;
        private String queryWhereColumnName;
        private String insertColumnName;
        private String deleteWhereValColumnName;
        private String deleteWhereColumnName;
        private String updateWhereValColumnName;
        private String updateWhereColumnName;
        private String updateColumn;
        private String updateValue;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getQueryWhereValColumnName() {
            return queryWhereValColumnName;
        }

        public void setQueryWhereValColumnName(String queryWhereValColumnName) {
            this.queryWhereValColumnName = queryWhereValColumnName;
        }

        public String getQueryWhereColumnName() {
            return queryWhereColumnName;
        }

        public void setQueryWhereColumnName(String queryWhereColumnName) {
            this.queryWhereColumnName = queryWhereColumnName;
        }

        public String getInsertColumnName() {
            return insertColumnName;
        }

        public void setInsertColumnName(String insertColumnName) {
            this.insertColumnName = insertColumnName;
        }

        public String getDeleteWhereValColumnName() {
            return deleteWhereValColumnName;
        }

        public void setDeleteWhereValColumnName(String deleteWhereValColumnName) {
            this.deleteWhereValColumnName = deleteWhereValColumnName;
        }

        public String getDeleteWhereColumnName() {
            return deleteWhereColumnName;
        }

        public void setDeleteWhereColumnName(String deleteWhereColumnName) {
            this.deleteWhereColumnName = deleteWhereColumnName;
        }

        public String getUpdateWhereValColumnName() {
            return updateWhereValColumnName;
        }

        public void setUpdateWhereValColumnName(String updateWhereValColumnName) {
            this.updateWhereValColumnName = updateWhereValColumnName;
        }

        public String getUpdateWhereColumnName() {
            return updateWhereColumnName;
        }

        public void setUpdateWhereColumnName(String updateWhereColumnName) {
            this.updateWhereColumnName = updateWhereColumnName;
        }

        public String getUpdateColumn() {
            return updateColumn;
        }

        public void setUpdateColumn(String updateColumn) {
            this.updateColumn = updateColumn;
        }

        public String getUpdateValue() {
            return updateValue;
        }

        public void setUpdateValue(String updateValue) {
            this.updateValue = updateValue;
        }
    }
}
