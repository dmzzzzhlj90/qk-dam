package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dm.dataingestion.service.PiciTaskDataFileSyncService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 原始层数据同步
 *
 * @author wjq
 * @date 20210608
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/sync/rizhi")
public class PiciTaskDataFileSyncController {
  private final PiciTaskDataFileSyncService piciTaskDataFileSyncService;

  @Autowired
  public PiciTaskDataFileSyncController(PiciTaskDataFileSyncService piciTaskDataFileSyncService) {
    this.piciTaskDataFileSyncService = piciTaskDataFileSyncService;
  }

  /**
   * 同步阿里云数据到腾讯云
   *
   * @param frontTabNamePatter,batchNum
   * @return DefaultCommonResult
   */
  @GetMapping("/files/data")
  public DefaultCommonResult syncRiZhiFilesData(
      @RequestParam(required = false) String frontTabNamePatter,
      @RequestParam(required = false) String batchNum) {
    String dataDay =
        DateTimeFormatter.ofPattern(LongGovConstant.DATE_TIME_PATTERN).format(LocalDateTime.now());
    piciTaskDataFileSyncService.syncPiciTaskFilesData(
        dataDay, frontTabNamePatter, batchNum, LongGovConstant.BUCKETNAME);
    return DefaultCommonResult.success();
  }
}
