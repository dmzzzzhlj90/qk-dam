package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiDataSourceConfigService;
import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_新建API
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create")
public class DasApiDataSourceConfigController {
    private final DasApiDataSourceConfigService dasApiDataSourceConfigService;

    @Autowired
    public DasApiDataSourceConfigController(
            DasApiDataSourceConfigService dasApiDataSourceConfigService) {
        this.dasApiDataSourceConfigService = dasApiDataSourceConfigService;
    }

    /**
     * 注册API_详情展示
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasDataSourceConfigVO>>
     */
    @GetMapping(value = "/query/by/{apiId}")
    public DefaultCommonResult<DasApiDataSourceConfigVO> getDasDataSourceConfigInfoByApiId(
            @PathVariable("apiId") String apiId) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiDataSourceConfigService.getDasDataSourceConfigInfoByApiId(apiId));
    }

    /**
     * 新增API基础信息
     *
     * @param dasDataSourceConfigVO
     * @return DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDasDataSourceConfig(
            @RequestBody @Validated DasApiDataSourceConfigVO dasDataSourceConfigVO) {
        dasApiDataSourceConfigService.addDasDataSourceConfig(dasDataSourceConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API基础信息
     *
     * @param dasDataSourceConfigVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDasDataSourceConfig(
            @RequestBody @Validated DasApiDataSourceConfigVO dasDataSourceConfigVO) {
        dasApiDataSourceConfigService.updateDasDataSourceConfig(dasDataSourceConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 新建API__请求参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/query/request/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDSConfigRequestParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigRequestParaHeaderInfo());
    }

    /**
     * 新建API__返回参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/query/response/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDSConfigResponseParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigResponseParaHeaderInfo());
    }

    /**
     * 新建API__排序参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/query/order/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDSConfigOrderParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigOrderParaHeaderInfo());
    }

    /**
     * 新建API__参数操作比较符号
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/query/paras/compare/symbol")
    public DefaultCommonResult<List<String>> getDSConfigParasCompareSymbol() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigParasCompareSymbol());
    }

  /**
   * 新建API__排序方式
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/query/paras/sort/style")
  public DefaultCommonResult<Map<String, String>> getDSConfigParasSortStyle() {
    return DefaultCommonResult.success(
            ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigParasSortStyle());
  }
}
