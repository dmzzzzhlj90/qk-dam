package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.PiciTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作数据引入日志表
 *
 * @author wjq
 * @date 2021/07/06
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/etl/task/log")
public class PiciTaskLogController {
  private final PiciTaskLogService piciTaskLogService;

  @Autowired
  public PiciTaskLogController(PiciTaskLogService piciTaskLogService) {
    this.piciTaskLogService = piciTaskLogService;
  }

  /**
   * 更新日志表is_hive_updated状态
   *
   * @param: frontTabNamePatter, batchNum
   * @return: DefaultCommonResult
   */
  @PutMapping("/modify/is_hive/by/{tableName}/{pici}")
  public DefaultCommonResult<Integer> modifyIsHiveByTableNameAndPici(
      @PathVariable(value = "tableName") String tableName,
      @PathVariable(value = "pici") String pici) {
    return new DefaultCommonResult(
        ResultCodeEnum.OK, piciTaskLogService.modifyIsHiveByTableNameAndPici(tableName, pici));
  }
}
