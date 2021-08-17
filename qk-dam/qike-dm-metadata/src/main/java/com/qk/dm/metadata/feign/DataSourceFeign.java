package com.qk.dm.metadata.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

// @FeignClient(name = "dm-datasource", url = "http://172.31.0.44:8780")
@FeignClient(value = "dm-datasource", path = "/datasource")
public interface DataSourceFeign {

  @GetMapping("/atlas/getdatatype")
  DefaultCommonResult<ArrayList<String>> getdatatype();
}
