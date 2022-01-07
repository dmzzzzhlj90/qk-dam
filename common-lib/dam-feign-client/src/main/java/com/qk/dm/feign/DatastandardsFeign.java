package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value ="dm-datastandards-${spring.profiles.active}",path = "/dsd")
public interface DatastandardsFeign {
  /**
   * 获取数据标准
   * @param dsdBasicInfoParamsDTO
   * @return
   */
  @PostMapping(value = "/basic/info/list")
  DefaultCommonResult<PageResultVO<DsdBasicInfoVO>> searchList(@RequestBody DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO);

  /**
   * 获取数据标准分类目录树
   *
   * @return DefaultCommonResult<List < DataStandardTreeVO>>
   */
  @GetMapping("/dir/list")
  DefaultCommonResult<List<DataStandardTreeVO>> searchList();

}
