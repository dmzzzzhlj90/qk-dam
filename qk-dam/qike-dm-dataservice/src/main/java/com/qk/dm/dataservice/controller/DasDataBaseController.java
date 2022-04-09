package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.service.DataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库信息
 *
 * @author wjq
 * @date 20220222
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/data/base")
public class DasDataBaseController {

    private final DataBaseService dataBaseService;

    public DasDataBaseController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    /**
     * 获取数据源连接类型
     *
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/connect/types")
    public DefaultCommonResult<List<String>> getAllConnType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllConnectType());
    }

    /**
     * 获取数据源连接
     *
     * @param connectType
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/connect/{type}")
    public DefaultCommonResult<List<String>> getResultDataSourceByType(@PathVariable("type") String connectType) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataSource(connectType));
    }

    /**
     * 获取db库列表
     *
     * @param connectType
     * @param dataSourceName
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("")
    public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(@RequestParam("connectType") String connectType,
                                                              @RequestParam("dataSourceName") String dataSourceName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataBase(connectType, dataSourceName));
    }

    /**
     * 获取table表列表
     *
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/table")
    public DefaultCommonResult<List<MtdTables>> getAllTable(@RequestParam("connectType") String connectType,
                                                            @RequestParam("dataSourceName") String dataSourceName,
                                                            @RequestParam("dataBaseName") String dataBaseName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllTable(connectType, dataSourceName, dataBaseName));
    }

    /**
     * 获取column字段列表
     *
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param tableName
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/column")
    public DefaultCommonResult<List<String>> getAllColumn(@RequestParam("connectType")String connectType,
                                                          @RequestParam("dataSourceName") String dataSourceName,
                                                          @RequestParam("dataBaseName") String dataBaseName,
                                                          @RequestParam("tableName") String tableName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllColumn(connectType, dataSourceName, dataBaseName, tableName));
    }
}
