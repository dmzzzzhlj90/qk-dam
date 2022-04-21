package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.DsDatasourceVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "dm-datasource-zdm-${spring.profiles.active}", path = "/ds/datasource")
@Component
public interface DataSourceV2Feign {
    /**
     * 根据id查询数据源连接信息
     * @param id
     * @return DefaultCommonResult<List<DsDatasourceVO>>
     */
    @GetMapping("/{id}")
    DefaultCommonResult<List<DsDatasourceVO>> getDataSourceByDsname(@PathVariable("id") Integer id);
}
