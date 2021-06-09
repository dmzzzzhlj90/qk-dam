package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QkUpdatedAgg {
    private static final Log LOG = LogFactory.get("sql执行程序");
    public static void qkUpdatedBatchSql(String sqlValue){
        Db use = Db.use("qk_es_updated");
        LOG.info("开始尝试连接数据源【{}】", "qk_es_updated");
        LOG.info("连接成功！");
        String[] values = sqlValue.split("\n");
        List<String> sqls = Arrays.stream(values).
                filter(v -> v.startsWith("LOCK")||v.startsWith("INSERT")||v.startsWith("UNLOCK"))
                .collect(Collectors.toList());

        try {
            use.executeBatch(sqls);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
