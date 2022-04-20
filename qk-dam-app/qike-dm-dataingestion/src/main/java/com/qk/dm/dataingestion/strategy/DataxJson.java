package com.qk.dm.dataingestion.strategy;

import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.dataingestion.vo.DataMigrationVO;

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
}
