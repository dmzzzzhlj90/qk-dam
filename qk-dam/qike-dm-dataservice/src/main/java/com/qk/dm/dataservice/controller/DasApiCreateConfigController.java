package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_新建API_配置方式
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create/config")
public class DasApiCreateConfigController {
    private final DasApiCreateConfigService dasApiCreateConfigService;

    @Autowired
    public DasApiCreateConfigController(DasApiCreateConfigService dasApiCreateConfigService) {
        this.dasApiCreateConfigService = dasApiCreateConfigService;
    }


    /**
     * 新增API配置方式_详情展示
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasApiCreateVO>>
     */
    @GetMapping(value = "/query/{apiId}")
    public DefaultCommonResult<DasApiCreateConfigVO> getDasApiCreateConfigInfoByApiId(
            @PathVariable("apiId") String apiId) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateConfigInfoByApiId(apiId));
    }

    /**
     * 新增API配置方式
     *
     * @param dasApiCreateConfigVO
     * @return DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDasApiCreateConfig(@RequestBody @Validated DasApiCreateConfigVO dasApiCreateConfigVO) {
        dasApiCreateConfigService.addDasApiCreateConfig(dasApiCreateConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API配置方式
     *
     * @param dasApiCreateConfigVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDasApiCreateConfig(@RequestBody @Validated DasApiCreateConfigVO dasApiCreateConfigVO) {
        dasApiCreateConfigService.updateDasApiCreateConfig(dasApiCreateConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 新建API__请求参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/request/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDasApiCreateRequestParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateRequestParaHeaderInfo());
    }

    /**
     * 新建API__返回参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/response/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDasApiCreateResponseParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateResponseParaHeaderInfo());
    }

    /**
     * 新建API__排序参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/query/order/paras/header/infos")
    public DefaultCommonResult<Map<String, String>> getDasApiCreateOrderParaHeaderInfo() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateOrderParaHeaderInfo());
    }

    /**
     * 新建API__参数操作比较符号
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/paras/compare/symbol")
    public DefaultCommonResult<List<String>> getDasApiCreateParasCompareSymbol() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateParasCompareSymbol());
    }

    /**
     * 新建API__排序方式
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/paras/sort/style")
    public DefaultCommonResult<Map<String, String>> getDasApiCreateParasSortStyle() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getDasApiCreateParasSortStyle());
    }

    /**
     * 新建API__获取db库信息下拉列表
     *
     * @param dbType
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/database/all")
    public DefaultCommonResult<List<String>> getAllDataBase(@RequestParam("dbType") String dbType) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getAllDataBase(dbType));
    }

    /**
     * 新建API__获取table表信息下拉列表
     *
     * @param dbType,server,dbName
     * @return DefaultCommonResult
     */
    @GetMapping("/table/all")
    public DefaultCommonResult<List<String>> getAllTable(@RequestParam("dbType") String dbType,
                                                         @RequestParam("server") String server,
                                                         @RequestParam("dbName") String dbName) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getAllTable(dbType, server, dbName));
    }

    /**
     * 新建API__获取column字段信息下拉列表
     *
     * @param dbType,server,dbName
     * @return DefaultCommonResult
     */
    @GetMapping("/column/all")
    public DefaultCommonResult<List> getAllColumn(@RequestParam("dbType") String dbType,
                                                  @RequestParam("server") String server,
                                                  @RequestParam("dbName") String dbName,
                                                  @RequestParam("tableName") String tableName) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getAllColumn(dbType, server, dbName, tableName));
    }

    // ========================数据源服务API调用=====================================

    /**
     * 查询所有数据源连接类型
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/datasource/api/type/all")
    public DefaultCommonResult<List<String>> getAllConnType() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getAllConnType());
    }

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/datasource/api/database/{type}")
    DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
            @PathVariable("type") String type) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getResultDataSourceByType(type));
    }

    /**
     * 根据数据源名称获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/datasource/api/name/{connectName}")
    public DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
            @PathVariable("connectName") String connectName) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK,
                dasApiCreateConfigService.getResultDataSourceByConnectName(connectName));
    }

    // ========================数据源服务API调用=====================================

    /**
     * 获取所有的元数据类型
     *
     * @return DefaultCommonResult<List < MtdAtlasEntityType>>
     */
    @GetMapping("/mtd/api/all/entity/type")
    public DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType() {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.getAllEntityType());
    }

    /**
     * 获取元数据详情信息
     *
     * @param mtdApiParams
     * @return DefaultCommonResult<MtdApi>
     */
    @PostMapping("/mtd/detail")
    DefaultCommonResult<MtdApi> mtdDetail(@RequestBody @Validated MtdApiParams mtdApiParams) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dasApiCreateConfigService.mtdDetail(mtdApiParams));
    }

}