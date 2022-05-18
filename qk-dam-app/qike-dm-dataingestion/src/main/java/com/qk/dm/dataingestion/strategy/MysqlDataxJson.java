package com.qk.dm.dataingestion.strategy;

import com.google.gson.Gson;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataingestion.constant.IngestionConstant;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

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

    private final DataBaseInfoDefaultApi dataBaseService;

    public MysqlDataxJson(DataBaseInfoDefaultApi dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer = getDataSource(baseInfo.getSourceCode());
        ReaderPara readerPara;
        if(StringUtils.isNotBlank(baseInfo.getWhereCondition())){
            readerPara =  whereCondition(baseInfo,dataSourceServer,dataMigrationVO.getColumnList().getSourceColumnList());
        }else {
            readerPara =  mysqlReader(baseInfo,dataSourceServer,dataMigrationVO.getColumnList().getSourceColumnList());
        }

        return DataxChannel.builder()
                .name(DataIntoType.MYSQL_READER.getType())
                .parameter(readerPara)
                .build();

    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer = getDataSource(baseInfo.getTargetCode());

        List<WriterPara.Connection> connect = List.of(WriterPara.Connection.builder()
                .jdbcUrl(jdbcUrlString(dataSourceServer, baseInfo.getTargetDatabase()))
                .table(List.of(baseInfo.getTargetTable())).build());
        log.info("写入mysql，作业名称【{}】,是否自动创建表参数【{}】",baseInfo.getJobName(),baseInfo.getAutoCreate());
        //判断是否自动创建表
        if (Objects.equals(baseInfo.getAutoCreate(), IngestionConstant.AUTO_CREATE)) {
            //组装建表SQL
            String sqlScript = generateSql(baseInfo.getTargetTable(),
                    dataMigrationVO.getColumnList().getTargetColumnList());
            log.info("数据库类型【{}】，生成表SQL【{}】", baseInfo.getTargetTable(), sqlScript);
            createTable(jdbcUrlString(dataSourceServer, baseInfo.getTargetDatabase()), dataSourceServer, sqlScript);
        }
        return DataxChannel
                .builder()
                .name(DataIntoType.MYSQL_WRITER.getType())
                .parameter(getWriterPara(dataSourceServer,dataMigrationVO.getColumnList().getTargetColumnList(),
                connect,baseInfo))
                .build();
    }

    @Override
    public IngestionType ingestionType() {
        return IngestionType.MYSQL;
    }


    private List<String> getColumnList(List<ColumnVO.Column> columnList) {

        return Optional.ofNullable(columnList).orElse(List.of())
                .stream()
                .map(ColumnVO.Column::getName)
                .collect(Collectors.toList());
    }


    private DataSourceServer getDataSource(String dataSourceCode) {
        ResultDatasourceInfo sourceInfo = dataBaseService.getResultDataSourceByCode(dataSourceCode);
        if (Objects.isNull(sourceInfo)) {
            throw new BizException("数据源" + dataSourceCode + "不存在");
        }
        return new Gson().fromJson(sourceInfo.getConnectBasicInfoJson(), DataSourceServer.class);
    }

    /**
     * 获取写入参数
     * @param dataSourceServer 数据源
     * @param columnList 写入字段列表
     * @param connect 连接对象
     * @param baseInfo 作业基础对象
     * @return
     */
    private WriterPara getWriterPara(DataSourceServer dataSourceServer,List<ColumnVO.Column> columnList,
                              List<WriterPara.Connection> connect,DisMigrationBaseInfoVO baseInfo){
        //导入前是否删除
        if(Objects.equals(baseInfo.getTargetWriteMode(), IngestionConstant.IMPORT_BEFORE_DELETE)){
            return new WriterPara(dataSourceServer.getUserName(),
                    dataSourceServer.getPassword(),
                    getColumnList(columnList),
                    connect, "insert",List.of("delete from "+baseInfo.getTargetTable()));
        }else {
            return new WriterPara(dataSourceServer.getUserName(),
                    dataSourceServer.getPassword(),
                    getColumnList(columnList),
                    connect, "insert");
        }

    }

    /**
     * mysql 有where条件
     * @param baseInfo
     * @param dataSourceServer
     * @param columnList
     * @return
     */
    private ReaderPara whereCondition(DisMigrationBaseInfoVO baseInfo, DataSourceServer dataSourceServer,
                                      List<ColumnVO.Column> columnList){
        List<ReaderPara.Connection> connect = List.of(ReaderPara.Connection.builder()
                .jdbcUrl(jdbcUrl(dataSourceServer, baseInfo.getSourceDatabase()))
                .querySql(List.of(
                        generateSql(columnList,baseInfo.getSourceTable(),baseInfo.getWhereCondition()))
                ).build());

        return new ReaderPara(dataSourceServer.getUserName(),
                dataSourceServer.getPassword(),null,
                connect, baseInfo.getPrimaryField());
    }

    /**
     * 组装mysql reader 没有where条件
     * @param baseInfo
     * @param dataSourceServer
     * @param columnList
     * @return
     */
    private ReaderPara mysqlReader(DisMigrationBaseInfoVO baseInfo, DataSourceServer dataSourceServer,
                                   List<ColumnVO.Column> columnList){
        List<ReaderPara.Connection> connect = List.of(ReaderPara.Connection.builder()
                .jdbcUrl(jdbcUrl(dataSourceServer, baseInfo.getSourceDatabase()))
                .table(List.of(baseInfo.getSourceTable())).build());

        return new ReaderPara(dataSourceServer.getUserName(),
                dataSourceServer.getPassword(),
                getColumnList(columnList),
                connect, baseInfo.getPrimaryField());
    }

    private List<String> jdbcUrl(DataSourceServer dataSourceServer, String dataBaseName) {
        return List.of(jdbcUrlString(dataSourceServer, dataBaseName));
    }

    private String generateSql(List<ColumnVO.Column> columnList,String tableName,String whereCondition){
        String expression = columnList.stream().map(ColumnVO.Column::getName)
                .collect(Collectors.joining(","));
        log.info("导入前的SQL表达式【{}】,表名称【{}】，where条件【{}】",expression,tableName,whereCondition);
       return String.join(" ",
               "select",
               expression,
               "from",
               tableName,
               "where",
               whereCondition);
    }

    private String jdbcUrlString(DataSourceServer dataSourceServer, String dataBaseName) {
        return "jdbc:mysql://"
                + dataSourceServer.getServer() + ":"
                + dataSourceServer.getPort() + "/"
                + dataBaseName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    }
}
