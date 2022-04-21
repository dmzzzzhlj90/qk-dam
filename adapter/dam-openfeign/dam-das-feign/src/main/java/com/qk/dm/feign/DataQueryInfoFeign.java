package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm-dataservice-zdm-${spring.profiles.active}",path = "das")
@Component
public interface DataQueryInfoFeign {
    /**
     * 获取数据查询服务的Sql方式
     * @return
     */
    @PostMapping("/data/query/info")
    DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfo();

}
