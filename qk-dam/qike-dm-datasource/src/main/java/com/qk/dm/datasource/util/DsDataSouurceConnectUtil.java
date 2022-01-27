package com.qk.dm.datasource.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ElasticSearchVO;
import com.qk.dam.datasource.entity.HiveInfo;
import com.qk.dam.datasource.entity.MysqlInfo;
import com.qk.dam.datasource.entity.OracleInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dam.datasource.enums.DataSourceEnum;
import com.qk.dam.datasource.enums.ExceptionEnum;
import com.qk.dam.datasource.utils.DataSourcesUtil;
import com.qk.dm.datasource.vo.DsDatasourceVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

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
  public static void getDataSourceConnect(DsDatasourceVO dsDatasourceVO) {
    // 获取数据库连接类型
    String linkType = dsDatasourceVO.getLinkType();
    switch (linkType) {
      case DataSourcesUtil.MYSQL:
         connectMySQL(dsDatasourceVO,linkType);
        break;
      case DataSourcesUtil.HIVE:
         connectHive(dsDatasourceVO,linkType);
        break;
      default:
        throw new BizException("没有匹配的数据源参数类型");
    }
  }


  /**
   * 连接数据库
   *
   * @param linkType
   * @param url
   * @param user
   * @param password
   * @param driver
   * @return
   */
  private static void connection(String url, String user, String password, String driver,String linkType ) {
    Connection connection = null;
    try {
      Class.forName(driver);
      // 获取连接
      connection = DriverManager.getConnection(url, user, password);
    } catch (Exception exception) {
     dealException(exception,linkType);
    }finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }
  }

  private static void dealException(Exception exception, String linkType) {
    String name = getExceptionName(exception);
    String exceptionMessage = ExceptionEnum.fromValue(linkType, name);
    throw  new BizException(exceptionMessage);
  }

  private static String getExceptionName(Exception exception) {
    String exceptionName=null;
    String name = exception.getClass().getName();
    String[] split = name.split(Pattern.quote("."));
    Optional<String> exception1 = Arrays.asList(split).stream()
        .filter(s -> s.contains(DataSourcesUtil.EXCEPTION)).findFirst();
    if (exception1.isPresent()){
      exceptionName=exception1.get();
    }
    return exceptionName;
  }

  /**
   * hive做数据库连接测试
   *
   * @param dsDatasourceVO
   * @param linkType
   * @return
   */
  private static void connectHive(DsDatasourceVO dsDatasourceVO,
      String linkType) {
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
    connection(url, user, password, driver,linkType);
  }


  /**
   * mysql做数据库连接测试
   *
   * @return
   * @param dsDatasourceVO
   * @param linkType
   */
  private static void connectMySQL(DsDatasourceVO dsDatasourceVO,
      String linkType) {
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
    connection(url, user, password, driver,linkType);
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
