package com.qk.dam.sqlloader;

import static com.qk.dam.sqlloader.DmSqlLoader.*;
import static com.qk.dam.sqlloader.constant.LongGovConstant.LOCAL_FILES_PATH;
import static com.qk.dam.sqlloader.repo.PiciTaskLogAgg.qkLogPiciExecuting;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.model.*;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.repo.QkEtlAgg;
import com.qk.dam.sqlloader.repo.QkUpdatedAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SqlLoaderMain {
  private static final Log LOG = LogFactory.get("SqlLoaderMain");
  private static final int BATCH_SQL_SUBMIT = 20;
  private static final BlockingQueue<PiciTaskVO> FAIL_TASK = new LinkedBlockingQueue<>(100);

  public static int writeDestTaskAll() {
    List<PiciTaskVO> executePiciTask = getCosPiciTask();
    LOG.info("桶中有【{}】个任务", executePiciTask.size());

    return writeCosFileDest(executePiciTask);
  }

  public static int writeDestTask(final String updated) {
    LOG.info("执行的任务参数", updated);
    List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
    LOG.info("桶中有【{}】个任务", executePiciTask.size());

    return writeCosFileDest(executePiciTask);
  }

  public static int executeTarSqlUpdate(final String updated) {
    if (!FAIL_TASK.isEmpty()) {
      LOG.info("发现有失败的批次需重新执行！失败个数【{}】", FAIL_TASK.size());
      String tbName = "";
      while (!FAIL_TASK.isEmpty()) {
        PiciTaskVO piciTaskVO = FAIL_TASK.poll();
        if (piciTaskVO.getTableName().equals(tbName)) {
          // 上次 执行失败 跳出此次
          break;
        }
        executeTarSqlTableName(piciTaskVO.getTableName(), piciTaskVO.getPici());
        tbName = piciTaskVO.getTableName();
      }
    }

    LOG.info("开始获取桶中任务数据【{}】", updated);
    DmSqlLoader.refreshCosKeys();
    List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
    return executeTarSql(updated, executePiciTask);
  }

  public static int executeTarSqlTableName(final String tableName, final int pici) {
    LOG.info("开始获取桶中任务数据【{}】【{}】", tableName, pici);
    List<PiciTaskVO> executePiciTask = getCosPiciTask(tableName, pici);
    return executeTarSql(tableName, executePiciTask);
  }

  public static int executeTarSqlPici(final int pici) {
    LOG.info("开始获取桶中任务数据【{}】", pici);
    List<PiciTaskVO> executePiciTask = getCosPiciTask(pici);
    return executeTarSql(pici, executePiciTask);
  }

  public static int executeTarSqlTableName(final String tableName) {
    LOG.info("开始获取桶中任务数据【{}】", tableName);
    List<PiciTaskVO> executePiciTask = getCosTableTask(tableName);
    return executeTarSql(tableName, executePiciTask);
  }

  private static int executeTarSql(Object bt, List<PiciTaskVO> executePiciTask) {
    LOG.info("桶中有【{}】个任务", executePiciTask.size());
    for (PiciTaskVO piciTaskVO : executePiciTask) {
      LOG.info("批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
      Boolean aBoolean = PiciTaskLogAgg.taskTableExecuting(piciTaskVO.getTableName());
      if (!aBoolean) {
        COSObjectInputStream cosObjectStream =
            getCosObjectStream(getFileNameByOss(piciTaskVO.getOssPath()));

        executeSqlTask(piciTaskVO, cosObjectStream);
      } else {
        LOG.warn("表名【{}】有批次正在执行中...", piciTaskVO.getTableName());
      }
    }
    LOG.info("cos桶中【{}/】的tar均已执行完毕，", bt);
    return 1;
  }

  private static int writeCosFileDest(List<PiciTaskVO> executePiciTask) {
    for (PiciTaskVO piciTaskVO : executePiciTask) {
      COSObjectInputStream cosObjectStream =
          getCosObjectStream(getFileNameByOss(piciTaskVO.getOssPath()));
      try {
        TargzUtils.writeRtFile(
            cosObjectStream,
            LOCAL_FILES_PATH + piciTaskVO.getTableName() + "-" + piciTaskVO.getPici() + ".sql");
        LOG.info("sql文件下载成功,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
      } catch (Exception e) {
        LOG.info("sql文件下载失败,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
        e.printStackTrace();
      }
    }
    return 1;
  }

  private static Integer executeSqlTask(
      PiciTaskVO piciTaskVO, COSObjectInputStream cosObjectStream) {
    final List<String> insertSqls = new ArrayList<>(500);
    final List<String> replaceSqls = new ArrayList<>(500);
    AtomicInteger state = new AtomicInteger(1);
    AtomicLong atomicLongCount = new AtomicLong(-1);
    try {
      TargzUtils.readTarbgzSqls(
          cosObjectStream,
          (sql) -> {
            addSql(insertSqls, replaceSqls, sql);
            // sql回调
            if (insertSqls.size() == BATCH_SQL_SUBMIT) {
              // 满20 先提交一次 避免要提交的任务太多
              try {
                batchExecuteSql(piciTaskVO, insertSqls, atomicLongCount);
              } catch (SQLException throwables) {
                LOG.error("SQL执行异常");
                throwables.printStackTrace();
                state.set(0);
              } finally {
                insertSqls.clear();
              }
            }
          });
    } catch (IOException e) {
      LOG.error("解压tar发生错误");
      state.set(0);
      e.printStackTrace();
    }
    // 提交剩余未执行的SQL
    if (!insertSqls.isEmpty()) {
      try {
        batchExecuteSql(piciTaskVO, insertSqls, atomicLongCount);
      } catch (SQLException throwables) {
        LOG.error("SQL执行异常");
        state.set(0);
        throwables.printStackTrace();
      }
    }

    if (state.get() == 0) {
      // 异常状态程序终止
      List<String> piTbName = FAIL_TASK.stream().map(ft -> ft.getPici() + "-" + ft.getTableName()).collect(Collectors.toList());
      if (!piTbName.isEmpty()
              && !piTbName.contains(piciTaskVO.getPici() + "-" + piciTaskVO.getTableName())){
        boolean offer = FAIL_TASK.offer(piciTaskVO);
      }

      LOG.error("执行sql文件失败,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
      return 0;
    }
    // sql执行成功 日志状态改为1
    LOG.info("执行sql文件成功,批次【{}】表名【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName());
    PiciTaskLogAgg.saveQkLogPici(
        new PiciTaskLogVO(
            piciTaskVO.getPici(), piciTaskVO.getTableName(), 1, new Date(), new Date()));
    QkEtlAgg.procEsUpdateToUpdated1(piciTaskVO.getPici(), piciTaskVO.getTableName());
    return 1;
  }

  public static void procUpdateToUpdated1() {
    List<PiciTaskLogVO> piciTaskLogVOS = qkLogPiciExecuting();
    if (piciTaskLogVOS != null) {
      piciTaskLogVOS.forEach(
          piciTaskLogVO ->
              QkEtlAgg.procEsUpdateToUpdated1(
                  piciTaskLogVO.getPici(), piciTaskLogVO.getTableName()));
    }
  }

  private static void batchExecuteSql(
      PiciTaskVO piciTaskVO, List<String> insertSqls, AtomicLong atomicLongCount)
      throws SQLException {
    int[] countArray = QkUpdatedAgg.qkUpdatedBatchSql(insertSqls);
    long counts = IntStream.of(countArray).sum();
    LOG.info(
        "批次【{}】表名【{}】，分批次提交受影响行数【{}】", piciTaskVO.getPici(), piciTaskVO.getTableName(), counts);
    atomicLongCount.set(counts);
  }

  private static void addSql(List<String> insertSqls, List<String> replaceSqls, String sql) {
    insertSqls.add(sql);
    //        if (StringUtils.equalsIgnoreCase(SQL_INSERT, sql)) {
    //            insertSqls.add(sql);
    //        } else if (StringUtils.equalsIgnoreCase(SQL_INSERT, sql)) {
    //            replaceSqls.add(sql);
    //        }
  }
}
