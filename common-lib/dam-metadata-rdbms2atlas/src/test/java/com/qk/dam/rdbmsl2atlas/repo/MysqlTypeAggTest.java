package com.qk.dam.rdbmsl2atlas.repo;

import com.qk.dam.rdbmsl2atlas.QuickStart;
import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import org.apache.atlas.model.instance.AtlasEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MysqlTypeAggTest {
    @Test
    void addSimpleData() throws Exception {
        MysqlDbType mysqlDbType = new MysqlTypeAgg().searchMedataByDb("qk_es_updated");
        MysqlMetadata mysqlMetadata = new MysqlMetadata();
        AtlasEntity.AtlasEntitiesWithExtInfo entitiesWithExtInfo = mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType));
        new QuickStart().createEntities(entitiesWithExtInfo);
    }
}