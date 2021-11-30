//package com.qk.dm.dataservice.feign;
//
//import com.qk.dam.commons.http.result.DefaultCommonResult;
//import com.qk.dam.metedata.entity.MtdApi;
//import com.qk.dam.metedata.entity.MtdApiParams;
//import com.qk.dam.metedata.entity.MtdAtlasEntityType;
//import java.util.List;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(value = "dm-metadata-dev")
//public interface MetaDataFeign {
//
//  /**
//   * 获取所有的类型
//   *
//   * @return DefaultCommonResult<List < MtdAtlasEntityType>>
//   */
//  @GetMapping("/mtd/api/all/entity/type")
//  DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType();
//
//  /**
//   * 获取元数据详情信息
//   *
//   * @param mtdApiParams
//   * @return DefaultCommonResult<MtdApi>
//   */
//  @PostMapping("/mtd/api/detail")
//  DefaultCommonResult<MtdApi> mtdDetail(@RequestBody MtdApiParams mtdApiParams);
//}
