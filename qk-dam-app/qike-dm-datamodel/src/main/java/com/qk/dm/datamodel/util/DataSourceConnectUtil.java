package com.qk.dm.datamodel.util;

import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.enums.ConnTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 构建数据库连接并执行sql
 * @author zys
 * @date 2021/11/26 14:00
 * @since 1.0.0
 */
public class DataSourceConnectUtil {
  /**
   * 构建数据连接驱动并执行sql
   * @param dataConnectionType 数据库类型
   * @param ip 数据库地址ip
   * @param port 数据库端口
   * @param userName 用户名
   * @param passWord 用户密码
   * @param sql 执行sql
   * @param dataName 数据库名称
   * @throws SQLException
   */
  public static void test(String dataConnectionType,String ip,String port,String userName,String passWord,String sql,String dataName)
      throws SQLException {
    Connection conn = null;
    checkParams(dataConnectionType,ip,port,userName,passWord,sql,dataName);
    if (dataConnectionType.equals(ConnTypeEnum.MYSQL.getName())) {
      conn = connectMySQL(dataConnectionType,ip,port,userName,passWord,dataName);
    }else if (dataConnectionType.equals(ConnTypeEnum.HIVE.getName())) {
      conn = connectHive(dataConnectionType,ip,port,userName,passWord,dataName);
    }
    if (conn!=null){
      try {
        implementSql(conn,sql);
      } catch (Exception e) {
        e.printStackTrace();
        throw  new BizException("操作执行失败");
      }finally {
        conn.close();
      }
    }
  }

  /**
   * 获取hive的connection
   * @param dataConnectionType
   * @param ip
   * @param port
   * @param userName
   * @param passWord
   * @param dataName
   * @return
   */
  private static Connection connectHive(String dataConnectionType, String ip, String port, String userName, String passWord,
      String dataName) {
    Connection conn = null;
    // 获取数据源连接值
    String driver = getParamsByType(dataConnectionType); // 获取oracle数据驱动类
    String url =
        "jdbc:hive://" +ip + ":" + port+"/"+dataName; // 127.0.0.1是本机地址，
    conn = getConnect(url, userName, passWord, driver);
    return conn;
  }

  /**
   * 执行sql
   * @param conn
   * @param sql
   */
  private static void implementSql(Connection conn, String sql)
      throws SQLException {
    Statement statement = conn.createStatement();
    statement.execute(sql);
  }

  /**
   * 获取mySql的的connection
   * @param dataConnectionType
   * @param ip
   * @param port
   * @param userName
   * @param passWord
   * @param dataName
   * @return
   */
  private static Connection connectMySQL(String dataConnectionType, String ip,
      String port, String userName, String passWord, String dataName) {
    Connection conn = null;
    // 获取数据源连接值
    String driver = getParamsByType(dataConnectionType); // 获取oracle数据驱动类
    String url =
        "jdbc:mysql://" +ip + ":" + port+"/"+dataName; // 127.0.0.1是本机地址，
    conn = getConnect(url, userName, passWord, driver);
    return conn;
  }

  /**
   * 获取数据库连接
   * @param url
   * @param userName
   * @param passWord
   * @param driver
   * @return
   */
  private static Connection getConnect(String url, String userName, String passWord, String driver) {
    Connection connection = null;
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(url, userName, passWord); // 获取连接
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return connection;
  }

  /**
   * 校验参数
   * @param dataConnectionType
   * @param ip
   * @param port
   * @param userName
   * @param passWord
   * @param sql
   * @param dataName
   */
  private static void checkParams(String dataConnectionType, String ip, String port, String userName, String passWord, String sql,
      String dataName) {
    if (StringUtils.isEmpty(dataConnectionType)||StringUtils.isEmpty(ip)||StringUtils.isEmpty(port)||StringUtils.isEmpty(userName)||StringUtils.isEmpty(passWord)||StringUtils.isEmpty(sql)||StringUtils.isEmpty(dataName)){
      throw new BizException("创建数据库连接失败，参数不齐全");
    }
  }

  /**
   * 根据数据库类型获取对应的数据库驱动
   * @param type
   * @return
   */
  public static String getParamsByType(String type) {
    String driverInfo = null;
    if (type.equals("db-mysql")) {
      driverInfo = "com.mysql.cj.jdbc.Driver";
    } else if (type.equals("db-oracle")) {
      driverInfo = "oracle.jdbc.driver.OracleDriver";
    } else if (type.equals("db-hive")) {
      driverInfo = "org.apache.hive.jdbc.HiveDriver";
    } else if (type.equals("db-postgresql")) {
      driverInfo = "org.postgresql.Driver";
    } else {
      throw new BizException("没有匹配的数据源参数类型");
    }
    return driverInfo;
  }

  public static void main(String[] args) {
    try {
      test("db-mysql","127.0.0.1","3306","root","root","CREATE TABLE `user_test` (\n"
          + "  `id` int(11) NOT NULL,\n"
          + "  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,\n"
          + "  `age` int(11) DEFAULT NULL,\n" + "  PRIMARY KEY (`id`)\n"
          + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci","test");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new BizException("数据库操作执行失败");
    }
  }
}