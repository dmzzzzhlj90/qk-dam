package com.qk.dm.dataingestion.strategy;

import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import org.springframework.stereotype.Component;

@Component
public class HiveDataxJson implements DataxJson{

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
        return IngestionType.HIVE;
    }
}
