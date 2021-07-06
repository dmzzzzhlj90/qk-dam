package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Entity;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PiciTaskLogAgg {

  public static Boolean taskTableExecuting(String tablename) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE is_down=1 and tablename=?", tablename);
      return query.size() > 0;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  public static Integer saveQkLogPici(PiciTaskLogVO pici) {
    try {
      Entity uniqWhere =
          Entity.create("t_qk_datain_log")
              .set("pici", pici.getPici())
              .set("tablename", pici.getTableName());
      long count = QkEtlAgg.QK_ETL.count(uniqWhere);
      Entity entity = Entity.create("t_qk_datain_log").parseBean(pici);

      if (count == 0) {
        QkEtlAgg.QK_ETL.insert(entity);
      } else {
        QkEtlAgg.QK_ETL.update(entity, uniqWhere);
      }
      return 1;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return 0;
  }

  public static List<PiciTaskLogVO> qkLogPiciAll() {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query("SELECT * FROM t_qk_datain_log ORDER BY pici,tablename");

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPiciExecuting() {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log where is_down=1 ORDER BY pici,tablename");

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPiciUpdated() {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE is_down=0 ORDER BY pici,tablename");

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPiciUpdated(int pici, String updateTime) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE pici=? and date_format(updated, '%Y-%m-%d') = ? ORDER BY pici,tablename",
              pici, updateTime);

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPici(int pici) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE pici=? and is_down=0 ORDER BY pici,tablename",
              pici);

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPici(int pici, String tablename) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE pici=? and tablename=? and is_down=0  ORDER BY pici,tablename",
              pici,
              tablename);

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogTableName(String tablename) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE tablename=? and is_down=0 ORDER BY pici,tablename",
              tablename);

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPici(int pici, String tablename, int isDown) {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "SELECT * FROM t_qk_datain_log WHERE pici=? and tablename=? and is_down=? ORDER BY pici,tablename",
              pici,
              tablename,
              isDown);

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskLogVO> qkLogPiciAlarms() {
    try {
      List<Entity> query =
          QkEtlAgg.QK_ETL.query(
              "  SELECT * FROM t_qk_datain_log t "
                  + "LEFT JOIN ( SELECT max( a.pici ) AS max_pici, a.tablename FROM t_qk_datain_log a GROUP BY a.tablename ) B "
                  + "ON t.tablename = b.tablename WHERE t.pici < b.max_pici AND t.is_down != 2  ");

      List<PiciTaskLogVO> piciTaskLogs =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskLogVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getInt("is_down"),
                          entity.getDate("updated")))
              .collect(Collectors.toList());
      return piciTaskLogs;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

    public static int qkLogPiciModifyIsHive(String tableName, String pici) {
        try {
            Entity uniqWhere =
                    Entity.create("t_qk_datain_log")
                            .set("pici", pici)
                            .set("tablename", tableName);
            long count = QkEtlAgg.QK_ETL.count(uniqWhere);
            Entity entity = Entity.create().set("is_hive_updated", 1);
            QkEtlAgg.QK_ETL.update(entity, uniqWhere);
            return (int) count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
