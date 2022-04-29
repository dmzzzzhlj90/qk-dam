package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.rest.DatasourceBasicController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 访问数据源服务
 * 通过元数据服务获取。例如：库，表，列，字段
 *
 * @author zhudaoming
 */
@RestController
@RequestMapping("/datax")
public class DatasourceController extends DatasourceBasicController {

    /**
     * 获取所有数据连接
     * @return List<ResultDatasourceInfo> 所有数据连接
     */
    @GetMapping("/connects")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getResultAllDataSource() {

        return DefaultCommonResult.success(ResultCodeEnum.OK,Arrays.stream(IngestionType.values())
                .map(ingestionType ->
                        dataBaseService.getResultDataSourceByType(ingestionType.toString()))
                .flatMap(Collection::stream).collect(Collectors.toList()));
    }
}
