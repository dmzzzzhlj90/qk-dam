package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
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
   * @param mtdApiParams
   * @return DefaultCommonResult<MtdApi>
   */
  @PostMapping("/mtd/entity/detail")
  DefaultCommonResult<MtdApi> mtdDetail(@RequestBody MtdApiParams mtdApiParams);

  /**
   * 根据数据库类型和属性获取数据库信息
   * @param typeName
   * @param attrValue
   * @return
   */
  @GetMapping("/mtd/dbs/{typeName}/{attrValue}")
  DefaultCommonResult<MtdApi> getDbs(@PathVariable("typeName") String typeName, @PathVariable("attrValue") String attrValue);

  /**
   * 查询元数据是否存在该表和表中是否存在数据
   * @param mtdApiParams
   * @return
   */
  @PostMapping("/entity/exist/data")
  DefaultCommonResult<Integer> getExistData(@RequestBody @Validated MtdApiParams mtdApiParams);
}
