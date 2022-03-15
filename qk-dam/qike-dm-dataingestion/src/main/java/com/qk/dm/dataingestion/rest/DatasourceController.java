package com.qk.dm.dataingestion.rest;

/**
 * 访问数据源服务
 *
 * @author zhudaoming
 */

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.rest.DatasourceBasicController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/datax")
public class DatasourceController extends DatasourceBasicController {

    /**
     * 获取所有数据连接
     * @return List<ResultDatasourceInfo> 所有数据连接
     */
    @GetMapping("/connects")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType() {

        return DefaultCommonResult.success(ResultCodeEnum.OK,Arrays.stream(IngestionType.values())
                .map(ingestionType ->
                        dataBaseService.getResultDataSourceByType(ingestionType.toString()))
                .flatMap(Collection::stream).collect(Collectors.toList()));
    }
}
