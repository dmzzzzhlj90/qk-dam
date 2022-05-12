package com.qk.dm.datasource.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.catacollect.vo.ConnectInfoVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zys
 * @date 2022/4/14 16:56
 * @since 1.0.0
 */
public class HiveDbToTableAgg {
  private final Db use;
  private final String host;
  private final String db;
  public HiveDbToTableAgg(ConnectInfoVo connectInfoVo) {
    this.use =
        Db.use(
            new SimpleDataSource(
                "jdbc:hive2://"
                    +connectInfoVo.getHiveServer2()
                    + ":"
                    +connectInfoVo.getPort(),
                connectInfoVo.getUserName(),
                connectInfoVo.getPassword(),
                connectInfoVo.getDriverInfo()));
    host = connectInfoVo.getHiveServer2();
    db = connectInfoVo.getDatabaseName();
  }

  public List<Entity> searchHiveDB(List<Entity> list) {
    try {
      list = use.query("show databases");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<Entity> searchHiveTable(List<Entity> list) {
    try {
      list= use.query("show tables in "+db);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}