package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.enums.DataTypeMappingEnum;
import com.qk.dam.commons.enums.MysqlDataTypeEnum;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 获取hive所有的数据类型
     * @return DefaultCommonResult<Map<String, String>>
     */
    @GetMapping(value = "/hive")
    public DefaultCommonResult<Map<String, String>> getHiveDataType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, DataTypeEnum.getAllValue());
    }

    /**
     * 获取mysql所有的数据类型
     * @return DefaultCommonResult<Map<String, String>>
     */
    @GetMapping(value = "/mysql")
    public DefaultCommonResult<Map<String, String>> getMysqlDataType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, MysqlDataTypeEnum.getAllType());
    }

    /**
     * hive与mysql 数据类型映射
     * @param connectType 连接类型 mysql或hive
     * @param dataType 数据类型
     * @return
     */
    @GetMapping(value = "/mapping")
    public DefaultCommonResult<Map<String, String>> getDataTypeMapping(String connectType,
                                                                       String dataType){
        return DefaultCommonResult.success(ResultCodeEnum.OK, DataTypeMappingEnum.getDataType(connectType,dataType));
    }
}
