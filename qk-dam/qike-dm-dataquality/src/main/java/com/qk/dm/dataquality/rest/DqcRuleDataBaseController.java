package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.dataquality.service.DqcRuleDataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:07 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/db")
public class DqcRuleDataBaseController {

    private final DqcRuleDataBaseService dqcRuleDataBaseService;

    public DqcRuleDataBaseController(DqcRuleDataBaseService dqcRuleDataBaseService) {
        this.dqcRuleDataBaseService = dqcRuleDataBaseService;
    }

    /**
     * 查询所有数据源连接类型（计算引擎）
     *
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/types")
    public DefaultCommonResult<List<String>> getAllConnType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleDataBaseService.getAllConnType());
    }

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @param type
     * @return DefaultCommonResult<List < ResultDatasourceInfo>>
     */
    @GetMapping("/type/{type}")
    public DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
            @PathVariable("type") String type) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleDataBaseService.getResultDataSourceByType(type));
    }

//    /**
//     * 根据数据源名称获取数据源连接信息
//     *
//     * @param connectName
//     * @return DefaultCommonResult<ResultDatasourceInfo>
//     */
//    @GetMapping("/name/{connectName}")
//    public DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
//            @PathVariable("connectName") String connectName) {
//        return DefaultCommonResult.success(
//                ResultCodeEnum.OK, dqcRuleDataSourceService.getResultDataSourceByConnectName(connectName));
//    }

    // ========================元数据服务_API调用=====================================

//    /**
//     * 获取所有的元数据类型
//     *
//     * @return DefaultCommonResult<List < MtdAtlasEntityType>>
//     */
//    @GetMapping("/entity/type")
//    public DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType() {
//        return DefaultCommonResult.success(
//                ResultCodeEnum.OK, dqcRuleDataSourceService.getAllEntityType());
//    }
//
//    /**
//     * 获取元数据详情信息
//     *
//     * @param mtdApiParams
//     * @return DefaultCommonResult<MtdApi>
//     */
//    @GetMapping("/entity/detail")
//    public DefaultCommonResult<MtdApi> mtdDetail(MtdApiParams mtdApiParams) {
//        return DefaultCommonResult.success(
//                ResultCodeEnum.OK, dqcRuleDataSourceService.mtdDetail(mtdApiParams));
//    }

    /**
     * 获取db库信息下拉列表
     *
     * @param dbType
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/storehouse")
    public DefaultCommonResult<List<String>> getAllDataBase(String dbType) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleDataBaseService.getAllDataBase(dbType));
    }

    /**
     * 获取table表信息下拉列表
     *
     * @param dbType,server,dbName
     * @return DefaultCommonResult
     */
    @GetMapping("/table")
    public DefaultCommonResult<List<String>> getAllTable(String dbType, String server, String dbName) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleDataBaseService.getAllTable(dbType,server,dbName));
    }

    /**
     * 获取column字段信息下拉列表
     *
     * @param dbType,server,dbName,tableName
     * @return DefaultCommonResult
     */
    @GetMapping("/column")
    public DefaultCommonResult<List<String>> getAllColumn(String dbType, String server, String dbName, String tableName) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleDataBaseService.getAllColumn(dbType,server,dbName,tableName));
    }
}
