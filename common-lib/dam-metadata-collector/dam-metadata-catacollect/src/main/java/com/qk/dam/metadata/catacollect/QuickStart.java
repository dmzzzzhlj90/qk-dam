/*
package com.qk.dam.metadata.catacollect;

import com.qk.dam.metadata.catacollect.catacollect.MysqlMetadataApi;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

*/
/**
 * 执行mysql元数据获取同步-快速开始
 *
 * @author daomingzhu
 *//*

public class QuickStart{
  private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);

  public QuickStart(){
    super();
  }

  public static void main(String[] args) throws Exception {
    new QuickStart().runQuickstart(args);
  }

  */
/**
   * 执行
   *
   *//*

  void runQuickstart(String[] args) {
    ConnectInfoVo connectInfoVo =new ConnectInfoVo();
    connectInfoVo.setType("hive");
    connectInfoVo.setHiveServer2("10.0.31.102");
    connectInfoVo.setPort("10000");
    connectInfoVo.setUserName("duanduan");
    connectInfoVo.setPassword("duanduan");
    connectInfoVo.setDb("test");
    connectInfoVo.setDriverInfo("org.apache.hive.jdbc.HiveDriver");
    //connectInfoVo.setList(lista);
    //List<String> entities = MysqlMetadataApi.queryDB(connectInfoVo);
    List<String> list = MysqlMetadataApi.queryTable(connectInfoVo);
    //System.out.println("数据库连接存在的数据库数量为"+entities.size());
    System.out.println("获取数据库表数量"+list.size());
    */
/*MetadataConnectInfoVo metadataConnectInfoVo =new MetadataConnectInfoVo();
    metadataConnectInfoVo.setType("hive");
    metadataConnectInfoVo.setServer("10.0.20.102");
    metadataConnectInfoVo.setPort("10000");
    metadataConnectInfoVo.setUserName("root");
    metadataConnectInfoVo.setPassword("root");
    metadataConnectInfoVo.setDb("default");
    List<String> listTable = new ArrayList<>();
    listTable.add("qk_das_api_basic_info");
    listTable.add("qk_ds_dir");
    metadataConnectInfoVo.setTableList(listTable);
   //metadataConnectInfoVo.setAllNums("all");
    try {
      List<AtlasEntity.AtlasEntitiesWithExtInfo> list1 = MysqlMetadataApi.extractorAtlasEntitiesWith(metadataConnectInfoVo);
      System.out.println("获取元数据数量为"+list1.size());
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }*//*


  }

}
*/
