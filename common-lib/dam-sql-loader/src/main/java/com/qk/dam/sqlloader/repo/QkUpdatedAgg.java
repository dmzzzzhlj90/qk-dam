package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import java.sql.SQLException;
import java.util.List;

public class QkUpdatedAgg {
    public final static Db use = Db.use("qk_es_updated");
    public final static Db use1 = Db.use("qk_es_updated_1");
    public static void qkUpdatedBatchSql(List<String> sqls) throws SQLException {
        use.executeBatch(sqls);
    }

}
