package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm-dataservice-${spring.profiles.active}",path = "das")
@Component
public interface DataQueryInfoFeign {
    /**
     * 获取数据查询服务的Sql方式
     * @return
     */
    @PostMapping("/data/query/info/all")
    DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfo();

    @PostMapping("/data/query/info/{id}")
    DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfoById(@PathVariable("id") Long id);

    @PostMapping("/data/query/info/last/{time}")
    DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfoLast(@PathVariable("time") Long time);

}
