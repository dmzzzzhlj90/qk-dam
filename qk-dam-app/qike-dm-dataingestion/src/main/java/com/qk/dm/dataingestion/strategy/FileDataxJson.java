package com.qk.dm.dataingestion.strategy;

import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import org.springframework.stereotype.Component;

/**
 * 文件数据同步
 * @author wangzp
 * @date 2022/04/09 15:41
 * @since 1.0.0
 */
@Component
public class FileDataxJson implements DataxJson{
    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        return null;
    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {
        return null;
    }

    @Override
    public IngestionType ingestionType() {
        return null;
    }
}
