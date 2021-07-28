package com.qk.dam.rdbmsl2atlas;

import cn.hutool.core.lang.Assert;
import com.google.common.base.Joiner;
import com.qk.dam.rdbmsl2atlas.base.BaseClientConf;
import com.qk.dam.rdbmsl2atlas.extractor.MysqlMetaDataExtractor;
import org.apache.atlas.AtlasException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 */
public class QuickStart extends BaseClientConf {
  private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);

  public QuickStart() throws AtlasException {}

  public static void main(String[] args) throws Exception {
    new QuickStart().runQuickstart(args);
  }

  /**
   * 执行
   *
   * @throws Exception
   */
  void runQuickstart(String[] args) throws Exception {
    Assert.notEmpty(args, "任务参数不能为空！");
    LOG.info("快速开始任务，任务参数【{}】", Joiner.on(",").join(args));
    try {
      for (String jobName : args) {
        AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo =
            MysqlMetaDataExtractor.extractorAtlasEntitiesWith(jobName);
        this.createEntities(atlasEntitiesWithExtInfo);
        LOG.info("任务执行完毕，已提交元数据【{}】个", atlasEntitiesWithExtInfo.getEntities().size());
      }
    } finally {
      this.closeConnection();
    }
  }

  public void createEntities(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo)
      throws Exception {
    atlasClientV2.createEntities(atlasEntitiesWithExtInfo);
  }
}
