package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.sqlloader.DmSqlLoader;
import com.qk.dam.sqlloader.SqlLoaderMain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daomingzhu
 * @date 2021/06/09
 */
@RestController
@RequestMapping("/sqlloader")
public class SqlLoaderController {
  @GetMapping("/exec/{update}")
  public DefaultCommonResult<Integer> syncRiZhiFilesData(@PathVariable String update) {
    int state = SqlLoaderMain.executeTarSqlUpdate(update);
    return DefaultCommonResult.success(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/pici/{pici}")
  public DefaultCommonResult<Integer> executeTarSqlPici(@PathVariable int pici) {
    int state = SqlLoaderMain.executeTarSqlPici(pici);
    return DefaultCommonResult.success(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/tablename/{tableName}")
  public DefaultCommonResult<Integer> executeTarSqlTableName(@PathVariable String tableName) {
    int state = SqlLoaderMain.executeTarSqlTableName(tableName);
    return DefaultCommonResult.success(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/tablename/pici/{tableName}/{pici}")
  public DefaultCommonResult<Integer> executeTarSqlTableName(
      @PathVariable String tableName, @PathVariable int pici) {
    int state = SqlLoaderMain.executeTarSqlTableName(tableName, pici);
    return DefaultCommonResult.success(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/writeDest/{date}")
  public DefaultCommonResult<Object> writeDestTask(@PathVariable String date) {
    SqlLoaderMain.writeDestTask(date);
    return DefaultCommonResult.success();
  }

  @GetMapping("/refreshCosKeys")
  public DefaultCommonResult<Object> syncRiZhiFilesData() {
    DmSqlLoader.refreshCosKeys();
    return DefaultCommonResult.success();
  }

  @GetMapping("/procUpdateToUpdated1")
  public void procUpdateToUpdated1() {
    SqlLoaderMain.procUpdateToUpdated1();
  }
}
