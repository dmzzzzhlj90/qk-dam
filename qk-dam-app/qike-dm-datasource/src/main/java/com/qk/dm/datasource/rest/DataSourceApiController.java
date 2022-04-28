package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.datasource.service.DataSourceApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源服务对外提供接口
 *
 * @author wjq
 * @date 20210825
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class DataSourceApiController {
    private final DataSourceApiService dataSourceApiService;

    @Autowired
    public DataSourceApiController(DataSourceApiService dataSourceApiService) {
        this.dataSourceApiService = dataSourceApiService;
    }

    /**
     * 查询所有数据源连接类型
     *
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/type/all")
    public DefaultCommonResult<List<String>> getAllConnType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataSourceApiService.getAllConnType());
    }

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @param type
     * @return DefaultCommonResult<List < ResultDatasourceInfo>>
     */
    @GetMapping("/database/{type}")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
            @PathVariable("type") String type) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dataSourceApiService.getResultDataSourceByType(type));
    }

    /**
     * 根据数据源名称获取数据源连接信息
     *
     * @param dataSourceName
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/{dataSourceName}")
    public DefaultCommonResult<ResultDatasourceInfo> getDataSource(@PathVariable("dataSourceName") String dataSourceName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataSourceApiService.getDataSource(dataSourceName));
    }

    /**
     * 获取数据源集合
     *
     * @param dataSourceNames
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/dataSource/list")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getDataSourceList(@RequestParam("dataSourceNames") List<String> dataSourceNames) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataSourceApiService.getDataSourceList(dataSourceNames));
    }

    /**
     * 根据数据源标识id获取数据源连接信息
     *
     * @param connId
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/{connId}")
    public DefaultCommonResult<ResultDatasourceInfo> getDataSourceByConnId(@PathVariable("connId") String connId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataSourceApiService.getDataSourceByConnId(connId));
    }

    /**
     * 根据数据源标识id获取数据源集合
     *
     * @param connIds
     * @return DefaultCommonResult<ResultDatasourceInfo>
     */
    @GetMapping("/data/sources")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getDataSourceListByConnId(@RequestParam("connIds") List<String> connIds) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataSourceApiService.getDataSourceListByConnId(connIds));
    }

}
