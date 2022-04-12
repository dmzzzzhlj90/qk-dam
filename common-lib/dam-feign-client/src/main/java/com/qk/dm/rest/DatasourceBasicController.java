package com.qk.dm.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public abstract class DatasourceBasicController {


   @Autowired
   protected DataBaseService dataBaseService;
    /**
     * 获取数据源连接类型
     *
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("/connect/types")
    public DefaultCommonResult<List<String>> getAllConnType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllConnType());
    }

    /**
     * 获取数据源连接
     * @param type
     * @return DefaultCommonResult<List<String>>
     */
    @GetMapping("/connect/{type}")
    public DefaultCommonResult<List<String>> getResultDataSource(@PathVariable final String type) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataSource(type));
    }

    /**
     * 获取db库列表
     * @param connectType
     * @param dataSourceName
     * @return DefaultCommonResult<List<String>>
     */
    @GetMapping("")
    public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(String connectType, String dataSourceName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataBase(connectType, dataSourceName));
    }

    /**
     * 获取table表列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @return DefaultCommonResult<List<String>>
     */
    @GetMapping("/table")
    public DefaultCommonResult<List<MtdTables>> getAllTable(String connectType, String dataSourceName, String dataBaseName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllTable(connectType, dataSourceName, dataBaseName));
    }

    /**
     * 获取column字段列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param tableName
     * @return DefaultCommonResult<List<String>>
     */
    @GetMapping("/column")
    public DefaultCommonResult<List<String>> getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllColumn(connectType, dataSourceName, dataBaseName, tableName));
    }

    /**
     * 根据guid获取column字段列表
     *
     * @param tableGuid 表的guid
     * @return DefaultCommonResult
     */
    @GetMapping("/guid/column")
    public DefaultCommonResult<List<String>> getColumnListByTableGuid(String tableGuid) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getColumnListByTableGuid(tableGuid));
    }
}
