package com.qk.dam.rdbmsl2atlas.repo;

import static org.junit.jupiter.api.Assertions.*;

import com.qk.dam.rdbmsl2atlas.QuickStart;
import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.conf.MetadataJobConf;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataJobYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.MysqlDataConnectYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import java.util.List;
import org.apache.atlas.model.instance.AtlasEntity;
import org.junit.jupiter.api.Test;

class MysqlTypeAggTest {
  @Test
  void addSimpleData() throws Exception {
    MetadataJobYamlVO job2 = MetadataJobConf.getMetadataJobYamlVO("job2");
    MysqlDataConnectYamlVO dataConnect = job2.getDataConnect();

    MysqlDbType mysqlDbType =
        new MysqlTypeAgg(dataConnect).searchMedataByDb(job2.getServerinfoYamlVO());

    MysqlMetadata mysqlMetadata = new MysqlMetadata();
    AtlasEntity.AtlasEntitiesWithExtInfo entitiesWithExtInfo =
        mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType));

    assertNotNull(entitiesWithExtInfo, "ok!");
    new QuickStart().createEntities(entitiesWithExtInfo);
  }
}
