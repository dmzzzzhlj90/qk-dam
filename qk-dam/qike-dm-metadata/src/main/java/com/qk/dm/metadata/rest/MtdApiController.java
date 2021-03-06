package com.qk.dm.metadata.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.*;
import com.qk.dm.metadata.service.MtdApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 其它服务调用的元数据接口（数据服务）
 *
 * @author wangzp
 * @date 2021/8/23 19:28
 * @since 1.0.0
 */
@RestController
public class MtdApiController {
  private final MtdApiService mtdApiService;

  public MtdApiController(MtdApiService mtdApiService) {
    this.mtdApiService = mtdApiService;
  }

  /**
   * 获取所有的类型
   *
   * @return DefaultCommonResult<List<MtdAtlasEntityTypeVO>> 元数据所有实体类型
   */
  @GetMapping("/entity/types")
//  @Auth(bizType = BizResource.MTD_ENTITY_TYPE, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType() {

    List<MtdAtlasEntityType> entityTypeList = mtdApiService.getAllEntityType();
    return DefaultCommonResult.success(ResultCodeEnum.OK, entityTypeList);
  }

  /**
   * 获取元数据详情信息
   *
   * @param mtdApiParams 实体参数
   * @return DefaultCommonResult<MtdApiVO> 元数据实体详情
   */
  @PostMapping("/entity/detail")
//  @Auth(bizType = BizResource.MTD_ENTITY, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdApi> mtdDetail(@RequestBody @Validated MtdApiParams mtdApiParams) {
    MtdApi mtdApi = mtdApiService.mtdDetail(
            mtdApiParams.getTypeName(),
            mtdApiParams.getDbName(),
            mtdApiParams.getTableName(),
            mtdApiParams.getServer());
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdApi);
  }

  /**
   * 查询表中是否存在数据
   * @param mtdApiParams
   * @return
   */
  @PostMapping("/entity/exist/data")
  public DefaultCommonResult<Integer> getDataLength(@RequestBody @Validated MtdApiParams mtdApiParams){
    Integer exist = mtdApiService.getExistData(mtdApiParams.getTypeName(),
            mtdApiParams.getDbName(),
            mtdApiParams.getTableName(),
            mtdApiParams.getServer());
    return DefaultCommonResult.success(ResultCodeEnum.OK,exist);
  }

}
