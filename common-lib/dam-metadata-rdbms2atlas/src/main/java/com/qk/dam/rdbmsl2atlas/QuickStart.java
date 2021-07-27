package com.qk.dam.rdbmsl2atlas;

import com.qk.dam.rdbmsl2atlas.base.BaseClientConf;
import org.apache.atlas.AtlasException;
import org.apache.atlas.model.instance.AtlasEntity;

/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 */
public class QuickStart extends BaseClientConf {

  public QuickStart() throws AtlasException {}

  public static void main(String[] args) throws Exception {
    new QuickStart().runQuickstart();
  }

  /**
   * 执行
   *
   * @throws Exception
   */
  void runQuickstart() throws Exception {
    try {
    } finally {
      this.closeConnection();
    }
  }

  public void createEntities(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo)
      throws Exception {
    atlasClientV2.createEntities(atlasEntitiesWithExtInfo);
  }
}
