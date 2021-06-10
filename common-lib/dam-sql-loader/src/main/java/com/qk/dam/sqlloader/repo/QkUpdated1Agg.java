package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QkUpdated1Agg {
    private static final Log LOG = LogFactory.get("sql执行程序");
    private final static Db use = Db.use("qk_es_updated_1");

    public static void qkUpdatedBatchSql()  {
        //68129428
        List<PiciTaskLogVO> piciTaskLogVOS = PiciTaskLogAgg.qkLogPiciAll();
        final List<String> sqls = new ArrayList<>();
        for (PiciTaskLogVO piciTaskLogVO : piciTaskLogVOS) {
            if (piciTaskLogVO.getIs_down()==1){
                int pici = piciTaskLogVO.getPici();
                String tableName = piciTaskLogVO.getTableName();
                String sql = "insert ignore into qk_es_updated_1."+tableName+"  select a.*,"+pici+",0 from qk_es_updated."+tableName+"  a ;";
                LOG.info("SQL:{}",sql);
                sqls.add(sql);
            }

        }

        try {
            use.executeBatch(sqls);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
