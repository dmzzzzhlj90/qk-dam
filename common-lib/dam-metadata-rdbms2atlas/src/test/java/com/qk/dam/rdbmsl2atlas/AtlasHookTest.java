package com.qk.dam.rdbmsl2atlas;

import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlColumnType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlTableType;
import java.util.List;
import org.apache.atlas.hook.AtlasHook;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.notification.HookNotification;
import org.junit.jupiter.api.Test;

public class AtlasHookTest extends AtlasHook {
  @Test
  public void ttt() throws Exception {
    super.notifyEntities(List.of(createEntity()), null);
  }

  HookNotification createEntity() throws Exception {

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

    return new HookNotification.EntityCreateRequestV2("dmz", entitiesWithExtInfo);
  }
}
