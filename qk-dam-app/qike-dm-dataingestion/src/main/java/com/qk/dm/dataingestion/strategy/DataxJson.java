package com.qk.dm.dataingestion.strategy;

import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataSourceServer;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.vo.ColumnVO;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import java.util.List;
import java.util.stream.Collectors;

public interface DataxJson {
    /**
     * 读取对象
     * @param dataMigrationVO 作业对象
     * @return datax读数据对象
     */
    DataxChannel getReader(DataMigrationVO dataMigrationVO);

    /**
     * 写入对象
     * @param dataMigrationVO 作业对象
     * @return datax写数据对象
     */
    DataxChannel getWriter(DataMigrationVO dataMigrationVO);


    IngestionType ingestionType();


    /**
     * 自动创建表
     * @param dataSourceServer
     */
    default void createTable(String jdbcUrl,DataSourceServer dataSourceServer,String sqlScript){
        try {
             Db.use(new SimpleDataSource(
                            jdbcUrl,
                            dataSourceServer.getUserName(),
                            dataSourceServer.getPassword(),
                            dataSourceServer.getDriverInfo()))
                     .execute(sqlScript);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 组装建表SQL语句
     * @param tableName
     * @param columnList
     * @return
     */
    default String generateSql(String tableName,List<ColumnVO.Column> columnList){
        List<Column> columns = columnList.stream()
                .map(e -> Column.builder()
                .name(e.getName()).dataType(e.getDataType()).build())
                .collect(Collectors.toList());

        return SqlBuilderFactory.creatTableSQL(
                Table.builder()
                        .name(tableName)
                        .columns(columns).build());
    }

}
