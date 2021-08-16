package com.qk.dm.datasource.util;

import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * 数据库测试连接
 *
 * @author zys
 * @date 2021/8/4 10:25
 * @since 1.0.0
 */
public class DsDataSouurceConnectUtil {
  public static final String DATA_SOURCE_MYSQL = "MySQL";
  public static final String DATA_SOURCE_ORACLE = "Oracle";
  public static final String DATA_SOURCE_HIVE = "Hive";
  public static final String DATA_SOURCE_POSTGRESQL = "PostgreSQL";
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
      if (linkType.equals(DATA_SOURCE_MYSQL)) {
        connect = connectMySQL(dsDatasourceVO);
      } else if (linkType.equals(DATA_SOURCE_ORACLE)) {
        connect = connectOracle(dsDatasourceVO);
      } else if (linkType.equals(DATA_SOURCE_HIVE)) {
        connect = connectHive(dsDatasourceVO);
      } else if (linkType.equals(DATA_SOURCE_POSTGRESQL)) {
        connect = connectPostSql(dsDatasourceVO);
      }
    } else {
      // logger.error("数据源连接类型为空");
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
    LinkedHashMap<String, String> res = dsDatasourceVO.getDataSourceValuesMap();
    String driver = (String) res.get("service"); // 获取postgetsql数据驱动类
    String url =
        "jdbc:postgresql://"
            + (String) res.get("server")
            + ":"
            + res.get("port")
            + "/"
            + res.get("dataBaseName"); // 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
    String user = (String) res.get("username");
    String password = (String) res.get("password");
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
    LinkedHashMap<String, String> res = dsDatasourceVO.getDataSourceValuesMap();
    String driver = (String) res.get("service"); // 获取oracle数据驱动类
    String url =
        "jdbc:hive://"
            + (String) res.get("server")
            + ":"
            + res.get("port")
            + "/"
            + res.get("dataBaseName"); // 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
    String user = (String) res.get("username"); // 连接oracle的用户名
    String password = (String) res.get("password"); // 连接oracle的密码
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
   * oracle做数据库连接测试
   *
   * @param dsDatasourceVO
   * @return
   */
  private static Boolean connectOracle(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    Connection connection = null;
    LinkedHashMap<String, String> res = dsDatasourceVO.getDataSourceValuesMap();
    String driver = (String) res.get("service"); // 获取oracle数据驱动类
    String url =
        "jdbc:oracle:thin:"
            + (String) res.get("server")
            + ":"
            + (String) res.get("prot")
            + ":"
            + res.get("dataBaseName"); // 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
    String user = (String) res.get("username"); // 连接oracle的用户名
    String password = (String) res.get("password"); // 连接oracle的密码
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
   * mysql做数据库连接测试
   *
   * @return
   * @param dsDatasourceVO
   */
  private static Boolean connectMySQL(DsDatasourceVO dsDatasourceVO) {
    Boolean connect = false;
    Connection conn = null;
    // 获取数据源连接值
    LinkedHashMap<String, String> res = dsDatasourceVO.getDataSourceValuesMap();
    String driver = (String) res.get("service"); // 获取mysql数据库的驱动类
    String url =
        "jdbc:mysql://"
            + (String) res.get("server")
            + ":"
            + (String) res.get("port")
            + "/"
            + res.get("dataBaseName"); // 连接数据库（qkdam是数据库名）
    String name = (String) res.get("username"); // 连接mysql的用户名
    String pwd = (String) res.get("password"); // 连接mysql的密码
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, name, pwd); // 获取连接对象
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } finally {
      if (conn != null) {
        connect = true;
        try {
          conn.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }
    return connect;
  }
}
