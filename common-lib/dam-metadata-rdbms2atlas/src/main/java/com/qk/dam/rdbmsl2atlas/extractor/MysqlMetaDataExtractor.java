package com.qk.dam.rdbmsl2atlas.extractor;

import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.conf.MetadataJobConf;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataJobYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.MysqlDataConnectYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.repo.MysqlTypeAgg;
import java.util.List;
import org.apache.atlas.model.instance.AtlasEntity;

/**
 * MySQL 元数据抽取类
 *
 * @author daomingzhu
 */
public class MysqlMetaDataExtractor {
  private MysqlMetaDataExtractor() {throw new IllegalStateException("Utility class");}

  public static AtlasEntity.AtlasEntitiesWithExtInfo extractorAtlasEntitiesWith(String jobName) {
    MysqlMetadata mysqlMetadata = new MysqlMetadata();
    return mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType(jobName)));
  }

  private static MysqlDbType mysqlDbType(String jobName) {
    MetadataJobYamlVO job2 = MetadataJobConf.getMetadataJobYamlVO(jobName);
    MysqlDataConnectYamlVO dataConnect = job2.getDataConnect();

    return new MysqlTypeAgg(dataConnect).searchMedataByDb(job2.getServerinfoYamlVO());
  }
}
