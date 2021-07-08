package com.qk.dm.dataingestion.scheduled;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.SqlLoaderMain;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dm.dataingestion.service.PiciTaskDataFileSyncService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时同步数据文件
 *
 * @author wjq
 * @date 20210615
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/timer/sync/")
@RefreshScope
public class TaskScheduledPiciDataFiles {
  private static final Log LOG = LogFactory.get("定时同步数据文件");

  @Value("${read.timer.param}")
  private String timerParam;

  private final PiciTaskDataFileSyncService piciTaskDataFileSyncService;

  @Autowired
  public TaskScheduledPiciDataFiles(PiciTaskDataFileSyncService piciTaskDataFileSyncService) {
    this.piciTaskDataFileSyncService = piciTaskDataFileSyncService;
  }

  /**
   * 定时同步阿里云数据到腾讯云,执行数据脚本;
   *
   * @return:
   */
  @GetMapping("/files/data")
  public void syncRiZhiFilesData() {
    LOG.info("定时同步开始!");
    String dataDay =
        DateTimeFormatter.ofPattern(LongGovConstant.DATE_TIME_PATTERN).format(LocalDateTime.now());
    try {
      int rtState =
          piciTaskDataFileSyncService.syncPiciTaskFilesData(
              dataDay, "", "", LongGovConstant.BUCKETNAME);
//      if (rtState == LongGovConstant.RESULT_SUCCESS_EXIST) {
//        LOG.info("开始执行脚本文件");
//        SqlLoaderMain.executeTarSqlUpdate(dataDay);
//        LOG.info("结束执行脚本文件");
//      }
    } catch (Exception e) {
      e.printStackTrace();
      LOG.info("定时同步出错!" + e.getMessage());
    }
    LOG.info("定时同步结束!");
  }
}
