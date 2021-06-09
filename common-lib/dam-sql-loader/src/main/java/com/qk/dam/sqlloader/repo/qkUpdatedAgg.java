package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class qkUpdatedAgg {
    public static void qkUpdatedBatchSql(String sqlValue){
        Db use = Db.use("qk_es_updated");
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
