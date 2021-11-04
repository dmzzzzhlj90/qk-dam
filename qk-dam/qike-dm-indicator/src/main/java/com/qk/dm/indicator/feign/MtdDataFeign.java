package com.qk.dm.indicator.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@FeignClient(value = "dm-metadata", path = "/mtd/api")
public interface MtdDataFeign {
   /**
    * 获取元数据表的信息
    * @param mtdTableApiParams
    * @return DefaultCommonResult<List<MtdTables>>
    */
   @PostMapping("/tables")
   DefaultCommonResult<List<MtdTables>> getTables(@RequestBody MtdTableApiParams mtdTableApiParams);
   /**
    * 获取表的字段信息
    * @param guid
    * @return
    */
   @GetMapping("/columns/{guid}")
   DefaultCommonResult<List<Map<String, Object>>> getColumns(@PathVariable("guid") String guid);
}
