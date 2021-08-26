package com.qk.dm.datasource.util;

import com.alibaba.nacos.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.HiveInfo;
import com.qk.dam.datasource.entity.MysqlInfo;
import com.qk.dam.datasource.entity.OracleInfo;
import com.qk.dam.datasource.entity.PostgresqlInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
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
    if (StringUtils.isNotBlank(linkType)) {
      if (linkType.equals(ConnTypeEnum.MYSQL.getName())) {
        connect = connectMySQL(dsDatasourceVO);
      } else if (linkType.equals(ConnTypeEnum.ORACLE.getName())) {
        connect = connectOracle(dsDatasourceVO);
      } else if (linkType.equals(ConnTypeEnum.HIVE.getName())) {
        connect = connectHive(dsDatasourceVO);
      } else if (linkType.equals(ConnTypeEnum.POSTGRESQL.getName())) {
        connect = connectPostSql(dsDatasourceVO);
      }
    } else {
      throw new BizException("数据源连接类型为空");
    }
    return connect;
  }

  /**
   * postgetsql做数据库连接测试
   *
   * @param dsDatasourceVO
   * @return
   */
  private static Boolean connectPostSql(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    Connection connection = null;
    ObjectMapper objectMapper = new ObjectMapper();
    PostgresqlInfo postgetsqlInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), PostgresqlInfo.class);
    String driver = postgetsqlInfo.getDriverInfo(); // 获取postgetsql数据驱动类
    String url =
        "jdbc:postgresql://"
            + postgetsqlInfo.getServer()
            + ":"
            + postgetsqlInfo.getPort(); // 127.0.0.1是本机地址，
    String user = postgetsqlInfo.getUserName();
    String password = postgetsqlInfo.getPassword();
    connect = getConnect(url, user, password, driver);
    return connect;
  }

  private static Boolean getConnect(String url, String user, String password, String driver) {
    Boolean connect = false;
    Connection connection = null;
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(url, user, password); // 获取连接
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
    Boolean connect = false;
    Connection connection = null;
    ObjectMapper objectMapper = new ObjectMapper();
    HiveInfo hiveInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), HiveInfo.class);
    String driver = hiveInfo.getDriverInfo(); // 获取hive数据驱动类
    String url =
        "jdbc:hive://" + hiveInfo.getServer() + ":" + hiveInfo.getPort(); // 127.0.0.1是本机地址，
    String user = hiveInfo.getUserName();
    String password = hiveInfo.getPassword();
    connect = getConnect(url, user, password, driver);
    return connect;
  }

  /**
   * oracle做数据库连接测试
   *
   * @param dsDatasourceVO
   * @return
   */
  private static Boolean connectOracle(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    Connection connection = null;
    ObjectMapper objectMapper = new ObjectMapper();
    OracleInfo oracleInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), OracleInfo.class);
    String driver = oracleInfo.getDriverInfo(); // 获取oracle数据驱动类
    String url =
        "jdbc:oracle:thin:"
            + oracleInfo.getServer()
            + ":"
            + oracleInfo.getPort(); // 127.0.0.1是本机地址，
    String user = oracleInfo.getUserName();
    String password = oracleInfo.getPassword();
    connect = getConnect(url, user, password, driver);
    return connect;
  }

  /**
   * mysql做数据库连接测试
   *
   * @return
   * @param dsDatasourceVO
   */
  private static Boolean connectMySQL(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    Connection conn = null;
    // 获取数据源连接值
    ObjectMapper objectMapper = new ObjectMapper();
    MysqlInfo mysqlInfo =
        objectMapper.convertValue(dsDatasourceVO.getConnectBasicInfo(), MysqlInfo.class);
    String driver = mysqlInfo.getDriverInfo(); // 获取oracle数据驱动类
    String url =
        "jdbc:mysql://" + mysqlInfo.getServer() + ":" + mysqlInfo.getPort(); // 127.0.0.1是本机地址，
    String user = mysqlInfo.getUserName();
    String password = mysqlInfo.getPassword();
    connect = getConnect(url, user, password, driver);
    return connect;
  }

  public static Map<String, String> getDataSourceType() {
    Map<String, String> map = new HashMap();
    map.put("MYSQL", ConnTypeEnum.MYSQL.getName());
    map.put("ORACLE", ConnTypeEnum.ORACLE.getName());
    map.put("DB2", ConnTypeEnum.DB2.getName());
    map.put("SQLSERVER", ConnTypeEnum.SQLSERVER.getName());
    map.put("HIVE", ConnTypeEnum.HIVE.getName());
    map.put("HBASE", ConnTypeEnum.HBASE.getName());
    map.put("REDIS", ConnTypeEnum.REDIS.getName());
    map.put("EXCEL", ConnTypeEnum.EXCEL.getName());
    map.put("CSV", ConnTypeEnum.CSV.getName());
    map.put("REST", ConnTypeEnum.REST.getName());
    return map;
  }

  public static Object getParamsByType(String type) {
    if (type.equals("db-mysql")) {
      MysqlInfo mysqlInfo = new MysqlInfo();
      mysqlInfo.setDriverInfo("com.mysql.cj.jdbc.Driver");
      return mysqlInfo;
    }
    if (type.equals("db-oracle")) {
      OracleInfo oracleInfo = new OracleInfo();
      oracleInfo.setDriverInfo("oracle.jdbc.driver.OracleDriver");
      return oracleInfo;
    }
    if (type.equals("db-hive")) {
      HiveInfo hiveInfo = new HiveInfo();
      hiveInfo.setDriverInfo("org.apache.hive.jdbc.HiveDriver");
      return hiveInfo;
    }
    if (type.equals("db-postgresql")) {
      PostgresqlInfo postgresqlInfo = new PostgresqlInfo();
      postgresqlInfo.setDriverInfo("org.postgresql.Driver");
      return postgresqlInfo;
    } else {
      throw new BizException("没有匹配的数据源参数类型");
    }
  }
}
