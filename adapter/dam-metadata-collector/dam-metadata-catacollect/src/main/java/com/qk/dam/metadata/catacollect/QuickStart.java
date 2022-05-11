package com.qk.dam.metadata.catacollect;

import cn.hutool.core.lang.Assert;
import com.google.gson.Gson;
import com.qk.dam.catacollect.vo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.catacollect.MysqlMetadataApi;
import com.qk.dam.metadata.catacollect.util.BaseClientConfUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 */

public class QuickStart {
  private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);
  public static void main(String[] args){
    Assert.notEmpty(args, "任务参数不能为空！");
    String arg = args[0];
    LOG.info("获取参数成功"+arg);
    MetadataConnectInfoVo metadataConnectInfoVo = new Gson().fromJson(arg, MetadataConnectInfoVo.class);
    LOG.info("数据转换成功"+metadataConnectInfoVo.toString());
    try {
      new QuickStart().runQuickstart(metadataConnectInfoVo);
    } catch (AtlasServiceException e) {
      e.printStackTrace();
      LOG.error("执行采集结果数据失败:【{}】",e.getLocalizedMessage());
      System.exit(-1);
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }finally {
      System.exit(0);
    }
  }

/**
   * 执行
   *
 * @param metadataConnectInfoVo
 */
  void runQuickstart(MetadataConnectInfoVo metadataConnectInfoVo)
      throws AtlasServiceException, SQLException {
    //1生成atals执行对象
    AtlasClientV2 atlasClientV2 = BaseClientConfUtil
        .getAtalsV2(metadataConnectInfoVo.getAtalsServer(),metadataConnectInfoVo.getAuth());
    //2执行采集方法
    MysqlMetadataApi.extractorAtlasEntitiesWith(metadataConnectInfoVo,atlasClientV2);
  }

}
