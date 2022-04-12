package com.qk.dm.dataingestion.strategy;

import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.dataingestion.model.hive.HiveBasePara;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisColumnInfoVO;
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
        HiveBasePara hiveReader = HiveBasePara.builder().column(getSourceColumnList(dataMigrationVO.getColumnList()))
                .defaultFS(baseInfo.getSourceDefaultFS()).path(baseInfo.getSourcePath())
                .fileType(baseInfo.getSourceFileType()).build();
        return DataxChannel.builder().name(HIVE_READER).parameter(hiveReader).build();

    }

    private List<HiveBasePara.Column> getSourceColumnList(List<DisColumnInfoVO> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(e-> HiveBasePara.Column.builder().name(e.getSourceName())
                     .type(e.getSourceType()).build()).collect(Collectors.toList());
        }

        return List.of();
    }


    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {

        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        HiveBasePara hiveWriter = HiveBasePara.builder().column(getTargetColumnList(dataMigrationVO.getColumnList()))
                .defaultFS(baseInfo.getTargetDefaultFS()).fileType(baseInfo.getTargetFileType())
                .path(baseInfo.getTargetPath()).fieldDelimiter(baseInfo.getTargetFieldDelimiter()).build();

        return DataxChannel.builder().name(HIVE_WRITER).parameter(hiveWriter).build();
    }

    private List<HiveBasePara.Column> getTargetColumnList(List<DisColumnInfoVO> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(e-> HiveBasePara.Column.builder().name(e.getTargetName())
                    .type(e.getTargetType()).build()).collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public IngestionType ingestionType() {
        return IngestionType.HIVE;
    }
}
