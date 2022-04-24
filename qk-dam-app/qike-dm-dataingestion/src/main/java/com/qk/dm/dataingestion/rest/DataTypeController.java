package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.DisDataTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据类型接口
 * @author wangzp
 * @date 2022/04/20 19:56
 * @since 1.0.0
 */
@RestController
@RequestMapping("/data/type")
public class DataTypeController {

    private final DisDataTypeService disDataTypeService;

    public DataTypeController(DisDataTypeService disDataTypeService) {
        this.disDataTypeService = disDataTypeService;
    }

    /**
     * 获取数据库的数据类型
     * @param connectType 连接类型
     * @return DefaultCommonResult<Map<String, String>>
     */
    @GetMapping("")
    public DefaultCommonResult<Map<String, String>> getDataType(@RequestParam("connectType") String connectType) {

        return DefaultCommonResult.success(ResultCodeEnum.OK,
                disDataTypeService.getDataType(connectType));
    }

    /**
     * hive与mysql 数据类型映射
     * @param sourceConnectType 源连接类型
     * @param targetConnectType 目标连接类型
     * @return DefaultCommonResult<Map<String,  List<String>>>>
     */
    @GetMapping(value = "/mapping")
    public DefaultCommonResult<Map<String, List<String>>> getDataTypeMapping(@RequestParam("sourceConnectType") String sourceConnectType,
                                                                             @RequestParam("targetConnectType") String targetConnectType){
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                disDataTypeService.getDataTypeMapping(sourceConnectType,targetConnectType));
    }
}
