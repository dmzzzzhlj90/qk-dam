package com.qk.dm.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.service.MxTaskPiCiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final MxTaskPiCiDataService mxTaskPiCiDataService;

  public MxTaskPiCiDataController(MxTaskPiCiDataService mxTaskPiCiDataService) {
    this.mxTaskPiCiDataService = mxTaskPiCiDataService;
  }

  @GetMapping("/task/pici")
  public DefaultCommonResult sendTaskPiCiMetrics() {
    mxTaskPiCiDataService.sendTaskPiCiMetrics();
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }
}
