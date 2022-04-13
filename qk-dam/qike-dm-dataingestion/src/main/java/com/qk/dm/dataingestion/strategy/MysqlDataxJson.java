package com.qk.dm.dataingestion.strategy;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataingestion.model.DataSourceServer;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.dataingestion.model.mysql.ReaderPara;
import com.qk.dm.dataingestion.model.mysql.WriterPara;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisColumnInfoVO;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
/**
 * mysql数据同步
 * @author wangzp
 * @date 2022/04/09 15:48
 * @since 1.0.0
 */
@Component
public class MysqlDataxJson implements DataxJson{
    public static String MYSQL_READER = "mysqlreader";
    public static String MYSQL_WRITER = "mysqlwriter";
    private final DataBaseInfoDefaultApi dataBaseService;

    public MysqlDataxJson(DataBaseInfoDefaultApi dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer = getDataSource(baseInfo.getSourceConnect());

        ArrayList<String> tables = Lists.newArrayList(baseInfo.getSourceTable());
        ArrayList<ReaderPara.Connection> conn = Lists.newArrayList(ReaderPara.Connection.builder()
                .jdbcUrl(jdbcUrl(dataSourceServer,baseInfo.getSourceDatabase()))
                .table(tables).build());

        ReaderPara reader = new ReaderPara(dataSourceServer.getUserName(), dataSourceServer.getPassword(), getSourceColumnList(dataMigrationVO.getColumnList()),
                conn, "id");

        return DataxChannel.builder().name(MYSQL_READER).parameter(reader).build();

    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer  = getDataSource(baseInfo.getTargetConnect());

        ArrayList<WriterPara.Connection> conn = Lists.newArrayList(WriterPara.Connection.builder()
                .jdbcUrl(jdbcUrlString(dataSourceServer,baseInfo.getTargetDatabase()))
                .table(Lists.newArrayList(baseInfo.getTargetTable())).build());
        WriterPara writer = new WriterPara(dataSourceServer.getUserName(), dataSourceServer.getPassword(), getTargetColumnList(dataMigrationVO.getColumnList()),
                conn, "insert");
        return DataxChannel.builder().name(MYSQL_WRITER).parameter(writer).build();
    }

    @Override
    public IngestionType ingestionType() {
        return IngestionType.MYSQL;
    }


    private List<String> getSourceColumnList(List<DisColumnInfoVO> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(DisColumnInfoVO::getSourceName).collect(Collectors.toList());
        }

        return List.of();
    }

    private List<String> getTargetColumnList(List<DisColumnInfoVO> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(DisColumnInfoVO::getTargetName).collect(Collectors.toList());
        }

        return List.of();
    }

    private DataSourceServer getDataSource(String connectName){
        ResultDatasourceInfo sourceInfo= dataBaseService.getDataSource(connectName);
        if(Objects.isNull(sourceInfo)){
            throw new BizException("数据源"+connectName+"不存在");
        }
        return new Gson().fromJson(sourceInfo.getConnectBasicInfoJson(),DataSourceServer.class);
    }

    private List<String> jdbcUrl(DataSourceServer dataSourceServer,String dataBaseName){
        return Lists.newArrayList(jdbcUrlString(dataSourceServer,dataBaseName));
    }

    private String jdbcUrlString(DataSourceServer dataSourceServer,String dataBaseName){
        return "jdbc:mysql://"+dataSourceServer.getServer()+":"
                +dataSourceServer.getPort()+"/"+dataBaseName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    }
}
