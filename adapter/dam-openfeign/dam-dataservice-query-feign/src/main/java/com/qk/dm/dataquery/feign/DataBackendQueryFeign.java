package com.qk.dm.dataquery.feign;

import com.qk.dam.model.HttpDataParamModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author zhudaoming
 */

@FeignClient(value = "dm-dataservice-query-${spring.profiles.active}")
@Component
public interface DataBackendQueryFeign {
    /**
     * 获取数据查询服务的Sql方式
     *
     * @return
     */
    @PostMapping("/app/query")
    String dataBackendQuery(@RequestBody HttpDataParamModel httpDataParamModel);
}
