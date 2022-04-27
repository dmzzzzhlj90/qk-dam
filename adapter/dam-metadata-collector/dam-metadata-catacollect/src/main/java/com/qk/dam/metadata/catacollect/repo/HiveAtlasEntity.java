package com.qk.dam.metadata.catacollect.repo;

import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.model.instance.AtlasEntity;

import java.util.List;

/**
 * 获取hive元数据信息
 * @author zys
 * @date 2022/4/15 10:25
 * @since 1.0.0
 */
public class HiveAtlasEntity {
  private final Db use;
  private final String db;
  public HiveAtlasEntity(MetadataConnectInfoVo metadataConnectInfoVo) {
    this.use =
        Db.use(
            new SimpleDataSource(
                "jdbc:hive2://"
                    +metadataConnectInfoVo.getServer()
                    + ":"
                    +metadataConnectInfoVo.getPort()
                    + "/information_schema",
                metadataConnectInfoVo.getUserName(),
                metadataConnectInfoVo.getPassword()));
    db = metadataConnectInfoVo.getDatabaseName();
  }

  public List<AtlasEntity.AtlasEntitiesWithExtInfo> searchHiveAtals(
      List<AtlasEntity.AtlasEntitiesWithExtInfo> list,
      AtlasClientV2 atlasClientV2,String atalsEnum, String value) {
    return list;
  }
}