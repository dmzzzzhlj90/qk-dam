package com.qk.dam.rdbmsl2atlas.extractor;

import cn.hutool.db.Entity;
import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.conf.MetadataJobConf;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataJobYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.MysqlDataConnectYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.repo.MysqlTypeAgg;
import org.apache.atlas.model.instance.AtlasEntity;

import java.util.List;

/**
 * MySQL 元数据抽取类
 *
 * @author daomingzhu
 */
public class MysqlMetaDataExtractor {
  private MysqlMetaDataExtractor() {
    throw new IllegalStateException("Utility class");
  }

  public static AtlasEntity.AtlasEntitiesWithExtInfo extractorAtlasEntitiesWith(
      String jobName, List<Entity> entityList) {
    MysqlMetadata mysqlMetadata = new MysqlMetadata();
    return mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType(jobName, entityList)));
  }

  public static List<Entity> getTableStrategy(String jobName) {
    MetadataJobYamlVO job2 = MetadataJobConf.getMetadataJobYamlVO(jobName);
    MysqlDataConnectYamlVO dataConnect = job2.getDataConnect();
    String table = dataConnect.getTable();
    if (table.equals("all") || table.startsWith("%") || table.endsWith("%")) {
      return new MysqlTypeAgg(dataConnect).getEntityList();
    }
    return new MysqlTypeAgg(dataConnect).searchMedataByDb();
  }

  private static MysqlDbType mysqlDbType(String jobName, List<Entity> entityList) {
    MetadataJobYamlVO job2 = MetadataJobConf.getMetadataJobYamlVO(jobName);
    MysqlDataConnectYamlVO dataConnect = job2.getDataConnect();
    MysqlTypeAgg mysqlTypeAgg = new MysqlTypeAgg(dataConnect);
    return mysqlTypeAgg.searchPatternTMedataByDb(entityList, job2.getServerinfo());
  }
}
