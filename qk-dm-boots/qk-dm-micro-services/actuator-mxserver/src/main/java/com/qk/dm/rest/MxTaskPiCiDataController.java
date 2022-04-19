package com.qk.dm.rest;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.MxTaskPiCiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjq
 * @date 2021/6/22 16:51
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/metrics")
public class MxTaskPiCiDataController {
  private static final Log LOG = LogFactory.get("定时监控数据同步");

  private final MxTaskPiCiDataService mxTaskPiCiDataService;

  @Autowired
  public MxTaskPiCiDataController(MxTaskPiCiDataService mxTaskPiCiDataService) {
    this.mxTaskPiCiDataService = mxTaskPiCiDataService;
  }

  @PostMapping("/task/pici")
  public DefaultCommonResult sendTaskPiCiMetrics() {
    LOG.info("定时监控开始!");
    mxTaskPiCiDataService.sendTaskPiCiMetrics();
    return DefaultCommonResult.success();
  }
}
