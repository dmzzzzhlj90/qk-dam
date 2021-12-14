package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "dm-metadata-${spring.profiles.active}")
@Component
public interface MetaDataFeign {

  /**
   * 获取所有的类型
   *
   * @return DefaultCommonResult<List < MtdAtlasEntityType>>
   */
  @GetMapping("/mtd/entity/types")
  DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType();

  /**
   * 获取元数据详情信息
   *
   * @param mtdApiParams 元数据参数
   * @return DefaultCommonResult<MtdApi>
   */
  @PostMapping("/mtd/entity/detail")
  DefaultCommonResult<MtdApi> mtdDetail(@RequestBody MtdApiParams mtdApiParams);

  /**
   * 根据数据库类型和属性获取数据库信息
   * @param typeName 类型名称
   * @param attrValue 属性值
   * @return 元数据API
   */
  @GetMapping("/mtd/dbs/{typeName}/{attrValue}")
  DefaultCommonResult<MtdApi> getDbs(@PathVariable("typeName") String typeName, @PathVariable("attrValue") String attrValue);
}
