package com.qk.dam.rdbmsl2atlas;

import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlColumnType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlTableType;
import java.util.List;
import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.kafka.KafkaNotification;
import org.apache.atlas.model.audit.EntityAuditEventV2;
import org.apache.atlas.model.discovery.AtlasQuickSearchResult;
import org.apache.atlas.model.discovery.QuickSearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.commons.configuration.Configuration;
import org.junit.jupiter.api.Test;

class QuickStartTest {

  @Test
  void addSimpleData() throws Exception {
    MysqlDbType mysqlDbType =
        MysqlDbType.builder()
            .name("dba")
            .displayName("dabaoi")
            .description("苗飒啊")
            .owner("zhudaoming")
            .qualifiedName("dba.accc@primary")
            .build();
    MysqlTableType mysqlTableType =
        MysqlTableType.builder()
            .name("table_a")
            .type("侧嗯啊啊")
            .displayName("assc")
            .createTime(System.currentTimeMillis())
            .description("assd")
            .qualifiedName("dba.table1@primary")
            .build();
    MysqlColumnType mysqlColumnType =
        MysqlColumnType.builder()
            .name("cl1")
            .displayName("ass")
            .position(0)
            .isNullable(false)
            .qualifiedName("dba.sss.column2@primary")
            .build();

    mysqlTableType.setMysqlColumnTypes(List.of(mysqlColumnType));
    mysqlDbType.setMysqlTableTypes(List.of(mysqlTableType));

    MysqlMetadata mysqlMetadata = new MysqlMetadata();
    AtlasEntity.AtlasEntitiesWithExtInfo entitiesWithExtInfo =
        mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType));
    new QuickStart().createEntities(entitiesWithExtInfo);
  }

  @Test
  void quickSearch() throws Exception {
    QuickStart q = new QuickStart();
    var quick = new QuickSearchParameters();
    quick.setQuery("sales_fac");
    quick.setLimit(5);
    quick.setOffset(0);
    AtlasQuickSearchResult atlasQuickSearchResult = q.atlasClientV2.quickSearch(quick);

    List<EntityAuditEventV2> auditEvents =
        q.atlasClientV2.getAuditEvents(
            "d35e008e-387c-4819-bea3-5775dd75d3d2", null, null, (short) 20);
    System.out.println(auditEvents);
  }

  @Test
  void runDeleteEntity() throws Exception {
    Configuration applicationProperties = ApplicationProperties.get();
    KafkaNotification kafkaNotification = new KafkaNotification(applicationProperties);
    try {
      kafkaNotification.start();
      Thread.sleep(2000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    QuickStart quick = new QuickStart();

    //        Map<String, String> c1 = Map.of("qualifiedName", "dba.accc@primary");
    //        quick.atlasClientV2.deleteEntityByAttribute("mysql_db", c1);
    //        Map<String, String> c2 = Map.of("qualifiedName", "dba.table1@primary");
    //        quick.atlasClientV2.deleteEntityByAttribute("mysql_table", c2);
    //        Map<String, String> c3 = Map.of("qualifiedName", "dba.sss.column2@primary");
    //        quick.atlasClientV2.deleteEntityByAttribute("mysql_column", c3);
    //
    //        AtlasSearchResult mysql_db = quick.atlasClientV2.dslSearchWithParams("from mysql_db",
    // 40, 0);
    //        Set<String> guids =
    // mysql_db.getEntities().stream().map(AtlasEntityHeader::getGuid).collect(Collectors.toSet());
    //        quick.atlasClientV2.purgeEntitiesByGuids(guids);
    //
    //        AtlasSearchResult mysql_table = quick.atlasClientV2.dslSearchWithParams("from
    // mysql_table", 40, 0);
    //        Set<String> tguids =
    // mysql_db.getEntities().stream().map(AtlasEntityHeader::getGuid).collect(Collectors.toSet());
    //        quick.atlasClientV2.purgeEntitiesByGuids(tguids);
    //
    //        AtlasSearchResult mysql_column = quick.atlasClientV2.dslSearchWithParams("from
    // mysql_column", 40, 0);
    //        Set<String> cguids =
    // mysql_db.getEntities().stream().map(AtlasEntityHeader::getGuid).collect(Collectors.toSet());
    //        quick.atlasClientV2.purgeEntitiesByGuids(cguids);
  }
}
