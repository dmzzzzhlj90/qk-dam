package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QkUpdated1Agg {
    private static final Log LOG = LogFactory.get("sql执行程序");
    private final static Db use = Db.use("qk_es_updated_1");

    public static void qkUpdatedBatchSql()  {
        //68129428
        List<PiciTaskLogVO> piciTaskLogVOS = PiciTaskLogAgg.qkLogPiciAll();

        for (PiciTaskLogVO piciTaskLogVO : piciTaskLogVOS) {
            if (piciTaskLogVO.getIs_down()==1){
                // 读取已经被下载的
                final List<String> sqls = new ArrayList<>();
                int pici = piciTaskLogVO.getPici();
                String tableName = piciTaskLogVO.getTableName();
                String sql = "insert ignore into qk_es_updated_1."+tableName+"  select a.*,"+pici+",0 from qk_es_updated."+tableName+"  a ;";
                LOG.info("SQL:{}",sql);
                sqls.add(sql);
                try {
                    use.executeBatch(sqls);
                    PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskLogVO.getPici(), piciTaskLogVO.getTableName(), 2, new Date()));
                    LOG.info("数据同步update1完成,批次【{}】表名【{}】",piciTaskLogVO.getPici(),piciTaskLogVO.getTableName());
                    // 清空update 表
                    String truncate = "truncate table qk_es_updated."+tableName+";";
                    LOG.info("SQL:{}",truncate);
                    use.execute(truncate,new Object[]{});
                    LOG.info("清空update完成,批次【{}】表名【{}】",piciTaskLogVO.getPici(),piciTaskLogVO.getTableName());


                } catch (SQLException throwables) {
                    LOG.info("数据同步失败,批次【{}】表名【{}】",piciTaskLogVO.getPici(),piciTaskLogVO.getTableName());
                    throwables.printStackTrace();
                }
            }

        }


    }
}
