package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;

import java.sql.SQLException;
import java.util.List;

public class QkUpdatedAgg {
    private final static Db use = Db.use("qk_es_updated");
    public static void qkUpdatedBatchSql(List<String> sqls) throws SQLException {
        use.executeBatch(sqls);
    }
}
