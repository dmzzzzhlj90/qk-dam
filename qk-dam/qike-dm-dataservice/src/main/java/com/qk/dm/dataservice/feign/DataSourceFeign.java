package com.qk.dm.dataservice.feign;


import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "dm-datasource", path = "/ds")
public interface DataSourceFeign {

    /**
     * 查询所有数据源连接类型
     *
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/datasource/type/all")
    DefaultCommonResult<List<String>> getAllConnType();

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @param type 数据连接类型
     * @return DefaultCommonResult<List < DsDatasourceVO>> 数据源信息
     */
    @GetMapping("/datasource/database/{type}")
    DefaultCommonResult getDataSourceByType(@PathVariable("type") String type);
}
