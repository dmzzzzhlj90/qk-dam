package com.qk.dam.sqlloader;

import cn.hutool.db.Db;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.model.*;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.repo.QkUpdatedAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.DmSqlLoader.*;
import static com.qk.dam.sqlloader.constant.LongGovConstant.LOCAL_FILES_PATH;

public class SqlLoaderMain {
    private static final Log LOG = LogFactory.get("SqlLoaderMain");

    public static int writeDestTask(final String updated) {
        LOG.info("执行的任务参数", updated);
        final String objKeyDate = updated.replace("-", "");

        List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
        LOG.info("桶中有【{}】个任务", executePiciTask.size());

        for (PiciTaskVO piciTaskVO : executePiciTask) {
            COSObjectInputStream cosObjectStream = getCosObjectStream(objKeyDate + "/" + getFileNameByOss(piciTaskVO.getOssPath()));
            try {
                TargzUtils.writeRtFile(cosObjectStream, LOCAL_FILES_PATH + piciTaskVO.getTableName() + "-" + piciTaskVO.getPici() + ".sql");
                LOG.info("sql文件下载成功,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
            } catch (Exception e) {
                LOG.info("sql文件下载失败,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
                e.printStackTrace();
            }
        }
        return 1;
    }

    public static int executeTarSqlTask(final String updated) {
        LOG.info("开始获取桶中任务数据【{}】", updated);
        final String objKeyDate = updated.replace("-", "");

        List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
        LOG.info("桶中有【{}】个任务", executePiciTask.size());
        for (PiciTaskVO piciTaskVO : executePiciTask) {
            LOG.info("批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
            COSObjectInputStream cosObjectStream = getCosObjectStream(objKeyDate + "/" + getFileNameByOss(piciTaskVO.getOssPath()));

            final List<String> insertSqls = new ArrayList<>(500);
            final List<String> replaceSqls = new ArrayList<>(500);
            AtomicInteger state = new AtomicInteger(1);
            try {
                TargzUtils.readTarbgzSqls(cosObjectStream, (sql) -> {
                    insertSqls.add(sql);
                    // sql回调
                    if (insertSqls.size() == 200) {
                        // 满200 先提交一次 避免要提交的任务太多
                        try {
                            QkUpdatedAgg.qkUpdatedBatchSql(insertSqls);
                        } catch (SQLException throwables) {
                            LOG.info("SQL执行异常");
                            throwables.printStackTrace();
                            state.set(0);
                        } finally {
                            insertSqls.clear();
                        }
                    }

                });
            } catch (IOException e) {
                LOG.info("解压tar发生错误");
                state.set(0);
                e.printStackTrace();
            }
            // 提交剩余未执行的SQL
            if (insertSqls.size() > 0) {
                try {
                    QkUpdatedAgg.qkUpdatedBatchSql(insertSqls);
                } catch (SQLException throwables) {
                    LOG.info("SQL执行异常");
                    state.set(0);
                    throwables.printStackTrace();
                }
            }
            if (state.get() == 0) {
                // 异常状态程序终止
                LOG.info("执行sql文件失败,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
                return 0;
            }
            // sql执行成功 日志状态改为1
            LOG.info("执行sql文件成功,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
            PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 1, new Date()));
        }
        LOG.info("cos桶中【{}/】的tar均已执行完毕，", updated);
        return 1;
    }

    public static void qkUpdatedBatchSql() {
        Db use = QkUpdatedAgg.use1;
        //68129428 68129428
        LOG.info("开始执行从update->update1的数据同步ETL");
        List<PiciTaskLogVO> piciTaskLogVOS = PiciTaskLogAgg.qkLogPiciAll();
        if (piciTaskLogVOS!=null){
            piciTaskLogVOS = piciTaskLogVOS.stream()
                    .filter(piciTaskLogVO -> piciTaskLogVO.getIs_down() == 1).collect(Collectors.toList());
            LOG.info("需要同步的任务有【{}】个",piciTaskLogVOS.size());
            for (PiciTaskLogVO piciTaskLogVO : piciTaskLogVOS) {
                final List<String> sqls = new ArrayList<>();
                int pici = piciTaskLogVO.getPici();
                String tableName = piciTaskLogVO.getTableName();
                String sql = "insert ignore into qk_es_updated_1." + tableName + "  select a.*," + pici + ",0 from qk_es_updated." + tableName + "  a ;";
                LOG.info("SQL:{}", sql);
                sqls.add(sql);
                try {
                    use.executeBatch(sqls);
                    PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskLogVO.getPici(), piciTaskLogVO.getTableName(), 2, new Date()));
                    LOG.info("数据同步update1完成,批次【{}】表名【{}】", piciTaskLogVO.getPici(), piciTaskLogVO.getTableName());

                    // 清空update 表
                    String truncate = "truncate table qk_es_updated." + tableName + ";";
                    LOG.info("SQL:{}", truncate);
                    use.execute(truncate, new Object[]{});
                    LOG.info("清空update完成,批次【{}】表名【{}】", piciTaskLogVO.getPici(), piciTaskLogVO.getTableName());


                } catch (SQLException throwables) {
                    LOG.info("数据同步失败,批次【{}】表名【{}】", piciTaskLogVO.getPici(), piciTaskLogVO.getTableName());
                    throwables.printStackTrace();
                }
            }
        }else {
            LOG.info("没有可以同步的任务日志");
        }
    }

}