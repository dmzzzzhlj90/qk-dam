package com.qk.dm.datasource.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.*;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dam.datasource.enums.DataSourceEnum;
import com.qk.dam.datasource.utils.DataSourcesUtil;
import com.qk.dm.datasource.vo.DsDatasourceVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库测试连接
 *
 * @author zys
 * @date 2021/8/4 10:25
 * @since 1.0.0
 */
public class DsDataSouurceConnectUtil {

  /**
   * 判定传入的数据源连接是否测试连接成功
   *
   * @param dsDatasourceVO
   * @return
   */
  public static Boolean getDataSourceConnect(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    // 获取数据库连接类型
    String linkType = dsDatasourceVO.getLinkType();
    switch (linkType) {
      case DataSourcesUtil.MYSQL:
        connect = connectMySQL(dsDatasourceVO);
        break;
      case DataSourcesUtil.HIVE:
        connect = connectHive(dsDatasourceVO);
        break;
      default:
        throw new BizException("没有匹配的数据源参数类型");
    }
    return connect;
  }


  /**
   * 连接数据库
   * @param url
   * @param user
   * @param password
   * @param driver
   * @return
   */
  private static Boolean getConnect(String url, String user, String password, String driver) {
    Boolean connect = false;
    Connection connection = null;
    try {
      Class.forName(driver);
      // 获取连接
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } finally {
      if (connection != null) {
        connect = true;
        try {
          connection.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }
    return connect;
  }

  /**
   * hive做数据库连接测试
   *
   * @param dsDatasourceVO
   * @return
   */
  private static Boolean connectHive(DsDatasourceVO dsDatasourceVO) {
    ObjectMapper objectMapper = new ObjectMapper();
    HiveInfo hiveInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), HiveInfo.class);
    String driver=DataSourceEnum.fromValue(hiveInfo.getType()).getDriver(); // 获取hive数据驱动类
    String url =
        DataSourceEnum.fromValue(hiveInfo.getType()).getCold()
            + hiveInfo.getServer()
            + DataSourcesUtil.DOUHAO
            + hiveInfo.getPort(); // 127.0.0.1是本机地址，
    String user = hiveInfo.getUserName();
    String password = hiveInfo.getPassword();
    Boolean connect = getConnect(url, user, password, driver);
    return connect;
  }


  /**
   * mysql做数据库连接测试
   *
   * @return
   * @param dsDatasourceVO
   */
  private static Boolean connectMySQL(DsDatasourceVO dsDatasourceVO) {
    // 获取数据源连接值
    ObjectMapper objectMapper = new ObjectMapper();
    MysqlInfo mysqlInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), MysqlInfo.class);
    String driver=DataSourceEnum.fromValue(mysqlInfo.getType()).getDriver(); // 获取oracle数据驱动类
    String url =
        DataSourceEnum.fromValue(mysqlInfo.getType()).getCold()
            + mysqlInfo.getServer()
            + DataSourcesUtil.DOUHAO
            + mysqlInfo.getPort(); // 127.0.0.1是本机地址，
    String user = mysqlInfo.getUserName();
    String password = mysqlInfo.getPassword();
    Boolean connect = getConnect(url, user, password, driver);
    return connect;
  }

  public static Map<String, String> getDataSourceType() {
    Map<String, String> map = new HashMap();
    map.put("MYSQL", ConnTypeEnum.MYSQL.getName());
//    map.put("ORACLE", ConnTypeEnum.ORACLE.getName());
//    map.put("DB2", ConnTypeEnum.DB2.getName());
//    map.put("SQLSERVER", ConnTypeEnum.SQLSERVER.getName());
    map.put("HIVE", ConnTypeEnum.HIVE.getName());
    map.put("ELASTICSEARCH", ConnTypeEnum.ELASTICSEARCH.getName());
//    map.put("REDIS", ConnTypeEnum.REDIS.getName());
//    map.put("EXCEL", ConnTypeEnum.EXCEL.getName());
//    map.put("CSV", ConnTypeEnum.CSV.getName());
//    map.put("REST", ConnTypeEnum.REST.getName());
    return map;
  }

  public static String addDriverinfo(Object baseDataSourceTypeInfo, String type) {
    ObjectMapper objectMapper = new ObjectMapper();
    switch (type) {
      case DataSourcesUtil.MYSQL:
        MysqlInfo mysqlInfo = objectMapper.convertValue(baseDataSourceTypeInfo, MysqlInfo.class);
        mysqlInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
        type = GsonUtil.toJsonString(mysqlInfo);
        break;
      case DataSourcesUtil.ORACLE:
        OracleInfo oracleInfo = objectMapper.convertValue(baseDataSourceTypeInfo, OracleInfo.class);
        oracleInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
        type = GsonUtil.toJsonString(oracleInfo);
        break;
      case DataSourcesUtil.HIVE:
        HiveInfo hiveInfo = objectMapper.convertValue(baseDataSourceTypeInfo, HiveInfo.class);
        hiveInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
        type = GsonUtil.toJsonString(hiveInfo);
        break;
      case DataSourcesUtil.ELASTICSEARCH:
        ElasticSearchVO elasticSearchVO = objectMapper.convertValue(baseDataSourceTypeInfo, ElasticSearchVO.class);
        elasticSearchVO.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
        type = GsonUtil.toJsonString(elasticSearchVO);
        break;
      default:
        throw new BizException("没有匹配的数据源参数类型");
    }
    return type;
  }
}
