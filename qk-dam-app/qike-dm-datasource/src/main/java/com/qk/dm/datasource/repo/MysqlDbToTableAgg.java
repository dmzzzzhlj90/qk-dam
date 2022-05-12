package com.qk.dm.datasource.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.catacollect.vo.ConnectInfoVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zys
 * @date 2022/4/14 16:41
 * @since 1.0.0
 */
public class MysqlDbToTableAgg {
  private final Db use;
  private final String db;
  public MysqlDbToTableAgg(ConnectInfoVo connectInfoVo) {
    this.use =
        Db.use(
            new SimpleDataSource(
                "jdbc:mysql://"
                    +connectInfoVo.getServer()
                    + ":"
                    +connectInfoVo.getPort()
                    + "/information_schema",
                connectInfoVo.getUserName(),
                connectInfoVo.getPassword()));
    db = connectInfoVo.getDatabaseName();
  }

  public List<Entity> searchMysqlDB(List<Entity> list) {
    try {
      list = use.query("select distinct TABLE_SCHEMA from tables");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<Entity> searchMysqlTable(List<Entity> list) {
    try {
      list = use.query("select * from TABLES im where im.table_schema=?", db);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}