package com.qk.dm.dataingestion.strategy;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataingestion.enums.DataIntoType;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataSourceServer;
import com.qk.dm.dataingestion.model.DataxChannel;

import com.qk.dm.dataingestion.model.mysql.ReaderPara;
import com.qk.dm.dataingestion.model.mysql.WriterPara;
import com.qk.dm.dataingestion.vo.ColumnVO;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * mysql数据同步
 *
 * @author wangzp
 * @date 2022/04/09 15:48
 * @since 1.0.0
 */
@Slf4j
@Component
public class MysqlDataxJson implements DataxJson {
    //自动创建表
    private static final String AUTO_CREATE = "1";
    private final DataBaseInfoDefaultApi dataBaseService;

    public MysqlDataxJson(DataBaseInfoDefaultApi dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer = getDataSource(baseInfo.getSourceConnect());

        List<ReaderPara.Connection> connect = List.of(ReaderPara.Connection.builder()
                .jdbcUrl(jdbcUrl(dataSourceServer, baseInfo.getSourceDatabase()))
                .table(List.of(baseInfo.getSourceTable())).build());

        ReaderPara reader = new ReaderPara(dataSourceServer.getUserName(),
                dataSourceServer.getPassword(),
                getColumnList(dataMigrationVO.getColumnList().getSourceColumnList()),
                connect, baseInfo.getPrimaryField());

        return DataxChannel.builder()
                .name(DataIntoType.MYSQL_READER.getType())
                .parameter(reader)
                .build();

    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer = getDataSource(baseInfo.getTargetConnect());

        List<WriterPara.Connection> connect = List.of(WriterPara.Connection.builder()
                .jdbcUrl(jdbcUrlString(dataSourceServer, baseInfo.getTargetDatabase()))
                .table(List.of(baseInfo.getTargetTable())).build());

        WriterPara writer = new WriterPara(dataSourceServer.getUserName(),
                dataSourceServer.getPassword(),
                getColumnList(dataMigrationVO.getColumnList().getTargetColumnList()),
                connect, "insert");
        //判断是否自动创建表
        if (Objects.equals(baseInfo.getAutoCreate(), AUTO_CREATE)) {
            //组装建表SQL
            String sqlScript = generateSql(baseInfo.getTargetTable(),
                    dataMigrationVO.getColumnList().getTargetColumnList());
            log.info("数据库类型【{}】，生成表SQL【{}】", baseInfo.getTargetTable(), sqlScript);
            createTable(jdbcUrlString(dataSourceServer, baseInfo.getTargetDatabase()), dataSourceServer, sqlScript);
        }
        return DataxChannel.builder().name(DataIntoType.MYSQL_WRITER.getType()).parameter(writer).build();
    }

    @Override
    public IngestionType ingestionType() {
        return IngestionType.MYSQL;
    }


    private List<String> getColumnList(List<ColumnVO.Column> columnList) {
        if (!CollectionUtils.isEmpty(columnList)) {
            return columnList.stream().map(ColumnVO.Column::getName).collect(Collectors.toList());
        }

        return List.of();
    }


    private DataSourceServer getDataSource(String connectName) {
        ResultDatasourceInfo sourceInfo = dataBaseService.getDataSource(connectName);
        if (Objects.isNull(sourceInfo)) {
            throw new BizException("数据源" + connectName + "不存在");
        }
        return new Gson().fromJson(sourceInfo.getConnectBasicInfoJson(), DataSourceServer.class);
    }

    private List<String> jdbcUrl(DataSourceServer dataSourceServer, String dataBaseName) {
        return Lists.newArrayList(jdbcUrlString(dataSourceServer, dataBaseName));
    }

    private String jdbcUrlString(DataSourceServer dataSourceServer, String dataBaseName) {
        return "jdbc:mysql://"
                + dataSourceServer.getServer() + ":"
                + dataSourceServer.getPort() + "/"
                + dataBaseName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    }
}
