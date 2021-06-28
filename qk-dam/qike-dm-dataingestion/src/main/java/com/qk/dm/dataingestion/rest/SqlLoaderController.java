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
  public DefaultCommonResult syncRiZhiFilesData(@PathVariable String update) {
    int state = SqlLoaderMain.executeTarSqlUpdate(update);
    return new DefaultCommonResult(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/pici/{pici}")
  public DefaultCommonResult executeTarSqlPici(@PathVariable int pici) {
    int state = SqlLoaderMain.executeTarSqlPici(pici);
    return new DefaultCommonResult(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/tablename/{tableName}")
  public DefaultCommonResult executeTarSqlTableName(@PathVariable String tableName) {
    int state = SqlLoaderMain.executeTarSqlTableName(tableName);
    return new DefaultCommonResult(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/tablename/pici/{tableName}/{pici}")
  public DefaultCommonResult executeTarSqlTableName(
      @PathVariable String tableName, @PathVariable int pici) {
    int state = SqlLoaderMain.executeTarSqlTableName(tableName, pici);
    return new DefaultCommonResult(ResultCodeEnum.OK, state);
  }

  @GetMapping("/exec/writeDest/{date}")
  public DefaultCommonResult writeDestTask(@PathVariable String date) {
    SqlLoaderMain.writeDestTask(date);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  @GetMapping("/refreshCosKeys")
  public DefaultCommonResult syncRiZhiFilesData() {
    DmSqlLoader.refreshCosKeys();
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }
}
