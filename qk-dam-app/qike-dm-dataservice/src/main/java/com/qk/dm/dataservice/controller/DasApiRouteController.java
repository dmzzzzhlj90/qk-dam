package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiRouteService;
import com.qk.dm.dataservice.vo.DasApiRouteVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_API路由Route匹配
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/route")
public class DasApiRouteController {
  private final DasApiRouteService dasApiRouteService;

  @Autowired
  public DasApiRouteController(DasApiRouteService dasApiRouteService) {
    this.dasApiRouteService = dasApiRouteService;
  }

  /**
   * 查询所有API路由匹配信息
   *
   * @return DefaultCommonResult<List < DaasApiTreeVO>>
   */
  @GetMapping("/list")
//  @Auth(bizType = BizResource.DAS_API_ROUTE, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<DasApiRouteVO>> searchList() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiRouteService.searchList());
  }

  /**
   * 新增API路由匹配信息
   *
   * @param dasApiRouteVO
   * @return DefaultCommonResult
   */
  @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_ROUTE, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody DasApiRouteVO dasApiRouteVO) {
    dasApiRouteService.insert(dasApiRouteVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API路由匹配信息
   *
   * @param dasApiRouteVO
   * @return DefaultCommonResult
   */
  @PutMapping("")
//  @Auth(bizType = BizResource.DAS_API_ROUTE, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody DasApiRouteVO dasApiRouteVO) {
    dasApiRouteService.update(dasApiRouteVO);
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除API路由匹配信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk")
//  @Auth(bizType = BizResource.DAS_API_ROUTE, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulk(@RequestParam("ids") List<String> ids) {
    dasApiRouteService.deleteBulk(ids);
    return DefaultCommonResult.success();
  }
}
