package com.qk.dm.dataingestion.strategy;

import com.google.gson.Gson;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataSourceServer;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.hive.HiveBasePara;
import com.qk.dm.dataingestion.vo.ColumnVO;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * hive datax 读写实现
 * @author wangzp
 * @date 2022/04/09 15:11
 * @since 1.0.0
 */
@Slf4j
@Component
public class HiveDataxJson implements DataxJson{
    private static final String HIVE_READER = "hdfsreader";
    private static final String HIVE_WRITER = "hdfswriter";
    //自动创建表
    private static final String AUTO_CREATE = "1";
    private final DataBaseInfoDefaultApi dataBaseService;

    public HiveDataxJson(DataBaseInfoDefaultApi dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        HiveBasePara hiveReader = HiveBasePara.builder()
                .column(getReaderColumnList(dataMigrationVO
                .getColumnList().getSourceColumnList()))
                .defaultFS(baseInfo.getSourceDefaultFS())
                .path(baseInfo.getSourcePath())
                .fileType(baseInfo.getSourceFileType()).build();
        return DataxChannel.builder().name(HIVE_READER).parameter(hiveReader).build();

    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {

        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        DataSourceServer dataSourceServer  = getDataSource(baseInfo.getTargetConnect());
        HiveBasePara hiveWriter = HiveBasePara.builder()
                .fileName(baseInfo.getTargetTable())
                .column(getWriterColumnList(dataMigrationVO.getColumnList().getTargetColumnList()))
                .defaultFS(baseInfo.getTargetDefaultFS())
                .fileType(baseInfo.getTargetFileType())
                .path(baseInfo.getTargetPath())
                .fieldDelimiter(baseInfo.getTargetFieldDelimiter())
                .writeMode(baseInfo.getTargetWriteMode()).build();

        //判断是否自动创建表
        if(Objects.equals(baseInfo.getAutoCreate(),AUTO_CREATE)) {
            //组装建表SQL
            String sqlScript = generateSql(baseInfo.getTargetTable(), dataMigrationVO.getColumnList().getTargetColumnList());
            log.info("数据库类型【{}】，生成表SQL【{}】", baseInfo.getTargetTable(), sqlScript);
            createTable(jdbcUrlString(dataSourceServer,baseInfo.getTargetDatabase()),dataSourceServer, sqlScript);
        }
        return DataxChannel.builder().name(HIVE_WRITER).parameter(hiveWriter).build();
    }


    @Override
    public IngestionType ingestionType() {
        return IngestionType.HIVE;
    }


    private List<HiveBasePara.Column> getReaderColumnList(List<ColumnVO.Column> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            List<HiveBasePara.Column> hiveColumnList = new ArrayList<>();
            for(int i=0;i<columnList.size();i++){
                hiveColumnList.add(HiveBasePara.Column.builder()
                        .type( columnList.get(i).getDataType()).index(i).build());
            }
            return hiveColumnList;
        }

        return List.of();
    }

    private List<HiveBasePara.Column> getWriterColumnList(List<ColumnVO.Column> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(e-> HiveBasePara.Column.builder().name(e.getName())
                    .type(e.getDataType()).build()).collect(Collectors.toList());
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


    private String jdbcUrlString(DataSourceServer dataSourceServer,String dataBaseName){
        return "jdbc:hive2://"
                +dataSourceServer.getServer()+":"
                +dataSourceServer.getPort()+"/"
                +dataBaseName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    }

}