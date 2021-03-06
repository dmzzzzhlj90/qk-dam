package com.qk.dam.rdbmsl2atlas;

import cn.hutool.core.lang.Assert;
import cn.hutool.db.Entity;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.qk.dam.rdbmsl2atlas.base.BaseClientConf;
import com.qk.dam.rdbmsl2atlas.extractor.MysqlMetaDataExtractor;
import org.apache.atlas.AtlasException;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 */
public class QuickStart extends BaseClientConf {
  private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);

  public QuickStart() throws AtlasException {
    super();
  }

  public static void main(String[] args) throws Exception {
    new QuickStart().runQuickstart(args);
  }

  /**
   * 执行
   *
   * @throws AtlasServiceException
   */
  void runQuickstart(String[] args) throws AtlasServiceException {
    Assert.notEmpty(args, "任务参数不能为空！");
    String str = String.format("快速开始任务，任务参数【%s】", Joiner.on(",").join(args));
    LOG.info(str);
    try {
      for (String jobName : args) {
        List<List<Entity>> entityList =
            Lists.partition(MysqlMetaDataExtractor.getTableStrategy(jobName), 2);

        for (List<Entity> entities : entityList) {
          AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo =
              MysqlMetaDataExtractor.extractorAtlasEntitiesWith(jobName, entities);

          this.createEntities(atlasEntitiesWithExtInfo);
          LOG.info("任务执行完毕，已提交元数据【{}】个", atlasEntitiesWithExtInfo.getEntities().size());
        }
      }
    } finally {
      this.closeConnection();
    }
  }

  public void createEntities(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo)
      throws AtlasServiceException {
    atlasClientV2.createEntities(atlasEntitiesWithExtInfo);
  }
}
