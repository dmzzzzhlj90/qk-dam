package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "dm-datasource-${spring.profiles.active}", path = "/ds/api")
@Component
public interface DataSourceFeign {

  /**
   * 查询所有数据源连接类型
   *
   * @return DefaultCommonResult<List < String>> 所有数据源列席
   */
  @GetMapping("/type/all")
  DefaultCommonResult<List<String>> getAllConnType();

  /**
   * 根据数据库类型获取数据源连接信息
   *
   * @param type 数据连接类型
   * @return DefaultCommonResult<List < DsDatasourceVO>> 数据源信息
   */
  @GetMapping("/database/{type}")
  DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
          @PathVariable("type") String type);

  /**
   * 根据数据源名称获取数据源连接信息
   *
   * @param connectName 数据源名称
   * @return DefaultCommonResult<List < DsDatasource>> 数据源信息
   */
  @GetMapping("/name/{connectName}")
  DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
          @PathVariable("connectName") String connectName);
}
