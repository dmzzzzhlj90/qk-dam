package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(@PathVariable("type") String type);

    /**
     * 根据数据源名称获取数据源连接信息
     *
     * @param dataSourceName 数据源名称
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/{dataSourceName}")
    DefaultCommonResult<ResultDatasourceInfo> getDataSource(@PathVariable("dataSourceName") String dataSourceName);

    /**
     * 获取数据源集合
     *
     * @param dataSourceNames
     * @return DefaultCommonResult<List<ResultDatasourceInfo>>
     */
    @GetMapping("/dataSource/list")
    DefaultCommonResult<List<ResultDatasourceInfo>> getDataSourceList(@RequestParam("dataSourceNames") List<String> dataSourceNames);

    /**
     * 根据数据源标识id获取数据源连接信息
     *
     * @param connId 数据源标识Id
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/data/sources/{connId}")
    DefaultCommonResult<ResultDatasourceInfo> getDataSourceByConnId(@PathVariable("connId") String connId);

    /**
     * 根据数据源标识id获取数据源集合
     *
     * @param connIds 数据源标识Id集合
     * @return DefaultCommonResult<List<ResultDatasourceInfo>>
     */
    @GetMapping("/data/sources")
    DefaultCommonResult<List<ResultDatasourceInfo>> getDataSourceListByConnId(@RequestParam("connIds") List<String> connIds);

}
