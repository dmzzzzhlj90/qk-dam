package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QkUpdatedAgg {
    private final static Db use = Db.use("qk_es_updated");
    private static final Log LOG = LogFactory.get("sql执行程序");
    public static void qkUpdatedBatchSql(String sqlValue) throws SQLException {
        LOG.info("开始尝试连接数据源【{}】", "qk_es_updated");
        LOG.info("连接成功！");
        String[] values = sqlValue.split("\n");
        List<String> sqls = Arrays.stream(values).
                filter(v -> !(v.startsWith("/*")||v.startsWith("LOCK")||v.startsWith("UNLOCK")))
                .filter(v->!"".equals(v))
                .collect(Collectors.toList());
            use.executeBatch(sqls);
    }
}
