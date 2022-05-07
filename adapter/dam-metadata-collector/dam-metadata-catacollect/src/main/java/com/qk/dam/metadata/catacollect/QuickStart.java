package com.qk.dam.metadata.catacollect;

import com.google.gson.Gson;
import com.qk.dam.catacollect.vo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.catacollect.MysqlMetadataApi;
import com.qk.dam.metadata.catacollect.util.BaseClientConfUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.atlas.AtlasServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 */

public class QuickStart{
  private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);

  public static void main(String[] args) throws Exception {
    String jsonconfig = args[0];
    MetadataConnectInfoVo metadataConnectInfoVo = new Gson().fromJson(jsonconfig, MetadataConnectInfoVo.class);
    new QuickStart().runQuickstart(metadataConnectInfoVo);
  }

/**
   * 执行
   *
 * @param metadataConnectInfoVo
 */

  void runQuickstart(MetadataConnectInfoVo metadataConnectInfoVo)
      throws AtlasException, AtlasServiceException, SQLException {
    /*MetadataConnectInfoVo metadataConnectInfoVo1 =new MetadataConnectInfoVo();
    metadataConnectInfoVo.setType("hive");
    metadataConnectInfoVo.setServer("10.0.20.102");
    metadataConnectInfoVo.setPort("10000");
    metadataConnectInfoVo.setUserName("root");
    metadataConnectInfoVo.setPassword("root");
    metadataConnectInfoVo.setDatabaseName("default");
    List<String> listTable = new ArrayList<>();
    listTable.add("qk_das_api_basic_info");
    listTable.add("qk_ds_dir");
    metadataConnectInfoVo.setTableList(listTable);*/
    //1生成atals执行对象
    AtlasClientV2 atlasClientV2 = BaseClientConfUtil.getAtalsV2(metadataConnectInfoVo.getAtalsServer(),metadataConnectInfoVo.getAuth());
    //2执行采集方法
    MysqlMetadataApi.extractorAtlasEntitiesWith(metadataConnectInfoVo,atlasClientV2);
  }

}
