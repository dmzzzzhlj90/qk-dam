package com.qk.dam.rdbmsl2atlas.repo;

import java.util.List;
import com.qk.dam.rdbmsl2atlas.QuickStart;
import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import org.apache.atlas.model.instance.AtlasEntity;
import org.junit.jupiter.api.Test;

class MysqlTypeAggTest {
    @Test
    void addSimpleData() throws Exception {
        MysqlDbType mysqlDbType = new MysqlTypeAgg()
                .searchMedataByDb("qk_es_updated",
                "qk_es",
                "172.31.0.16",
                "朱道明",
                "描述信息",
                "显示名称");

        MysqlMetadata mysqlMetadata = new MysqlMetadata();
        AtlasEntity.AtlasEntitiesWithExtInfo entitiesWithExtInfo = mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType));
        new QuickStart().createEntities(entitiesWithExtInfo);
    }
}