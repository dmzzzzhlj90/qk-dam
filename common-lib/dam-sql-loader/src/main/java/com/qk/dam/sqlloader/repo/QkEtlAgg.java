package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import java.sql.SQLException;
import java.util.Date;

public class QkEtlAgg {
  public static final Db QK_ETL = Db.use("qk_etl");
  private static final Log LOG = LogFactory.get("QkEtlAgg");

  public static void procEsUpdateToUpdated1(int pici, String tbName) {
    LOG.info("proc_es_update_to_updated_1开始执行,批次【{}】表名【{}】", pici, tbName);
    try {
      boolean state =
          SqlExecutor.call(
              QK_ETL.getConnection(),
              "{call proc_es_update_to_updated_1(?,?)}",
              new Object[] {pici, tbName});
      LOG.info("proc_es_update_to_updated_1执行成功,批次【{}】表名【{}】", pici, tbName);
      PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(pici, tbName, 2, new Date(), new Date()));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      LOG.info("执行存储过程proc_es_update_to_updated_1失败,批次【{}】表名【{}】", pici, tbName);
    }
  }
}
