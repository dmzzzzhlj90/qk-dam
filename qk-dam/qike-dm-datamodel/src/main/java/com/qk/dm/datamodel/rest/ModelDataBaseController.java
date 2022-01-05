package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicinfoParamsVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.service.DataBaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 获取数据源和元数据相关接口
 * @author wangzp
 * @date 2021/12/02 15:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/model/db")
public class ModelDataBaseController {

    private final DataBaseService dataBaseService;

    public ModelDataBaseController(DataBaseService dataBaseService){
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
     * @return DefaultCommonResult<List < ResultDatasourceInfo>>
     */
    @GetMapping("/connect/{type}")
    public DefaultCommonResult<Map<Integer,String>> getResultDataSourceByType(@PathVariable("type") String connectType) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataSources(connectType));
    }

    /**
     * 获取db库列表
     *
     * @param connectType
     * @return DefaultCommonResult<List < String>>
     */
    @GetMapping("")
    public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(String connectType, String dataSourceName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataBase(connectType, dataSourceName));
    }

    /**
     * 获取table表列表
     *
     * @param connectType,server,dataBaseName
     * @return DefaultCommonResult
     */
    @GetMapping("/table")
    public DefaultCommonResult<List<MtdTables>> getAllTable(String connectType, String dataSourceName, String dataBaseName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllTable(connectType, dataSourceName, dataBaseName));
    }

    /**
     * 获取column字段列表
     *
     * @param connectType,server,dataBaseName,tableName
     * @return DefaultCommonResult
     */
    @GetMapping("/column")
    public DefaultCommonResult<List<String>> getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllColumn(connectType, dataSourceName, dataBaseName, tableName));
    }

    //--------------------------------------------数据标准-------------------------------------------------------->

    /**
     * 获取数据标准
     * @param dsdBasicInfoParamsDTO
     * @return
     */
    @PostMapping("/standard")
    public DefaultCommonResult<PageResultVO<DsdBasicinfoParamsVO>>getStandard(@RequestBody DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO){
        return DefaultCommonResult.success(
            ResultCodeEnum.OK, dataBaseService.getStandard(dsdBasicInfoParamsDTO));
    }

    /**
     * 获取数据标准分类目录树
     *
     * @return DefaultCommonResult<List < DataStandardTreeVO>>
     */
    @GetMapping("/theme")
    public DefaultCommonResult<List<DataStandardTreeVO>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getTree());
    }
}
