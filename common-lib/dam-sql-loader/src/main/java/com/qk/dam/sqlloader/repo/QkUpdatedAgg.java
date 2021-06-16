package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import java.sql.SQLException;
import java.util.List;

public class QkUpdatedAgg {
    public final static Db use = Db.use("qk_es_updated");
    public static int[] qkUpdatedBatchSql(List<String> sqls) throws SQLException {
        return use.executeBatch(sqls);
    }

}
