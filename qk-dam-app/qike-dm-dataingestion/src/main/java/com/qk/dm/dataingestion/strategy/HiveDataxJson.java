package com.qk.dm.dataingestion.strategy;

import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.hive.HiveBasePara;
import com.qk.dm.dataingestion.vo.ColumnVO;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;


/**
 * hive datax 读写实现
 * @author wangzp
 * @date 2022/04/09 15:11
 * @since 1.0.0
 */
@Component
public class HiveDataxJson implements DataxJson{
    private static final String HIVE_READER = "hdfsreader";
    private static final String HIVE_WRITER = "hdfswriter";

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        HiveBasePara hiveReader = HiveBasePara.builder().column(getColumnList(dataMigrationVO
                .getColumnList().getSourceColumnList()))
                .defaultFS(baseInfo.getSourceDefaultFS()).path(baseInfo.getSourcePath())
                .fileType(baseInfo.getSourceFileType()).build();
        return DataxChannel.builder().name(HIVE_READER).parameter(hiveReader).build();

    }

    private List<HiveBasePara.Column> getColumnList(List<ColumnVO.Column> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(e-> HiveBasePara.Column.builder().name(e.getName())
                     .type(e.getDataType()).build()).collect(Collectors.toList());
        }

        return List.of();
    }


    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {

        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        HiveBasePara hiveWriter = HiveBasePara.builder().fileName(baseInfo.getTargetTable())
                .column(getColumnList(dataMigrationVO.getColumnList().getTargetColumnList()))
                .defaultFS(baseInfo.getTargetDefaultFS()).fileType(baseInfo.getTargetFileType())
                .path(baseInfo.getTargetPath()).fieldDelimiter(baseInfo.getTargetFieldDelimiter())
                .writeMode(baseInfo.getTargetWriteMode()).build();

        return DataxChannel.builder().name(HIVE_WRITER).parameter(hiveWriter).build();
    }



    @Override
    public IngestionType ingestionType() {
        return IngestionType.HIVE;
    }
}
