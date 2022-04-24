package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.enums.MysqlDataTypeEnum;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.DataType;
import com.qk.dm.dataingestion.service.DisDataTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据类型接口
 * @author wangzp
 * @date 2022/04/21 10:36
 * @since 1.0.0
 */
@Slf4j
@Service
public class DisDataTypeServiceImpl implements DisDataTypeService {

    @Override
    public Map<String, String> getDataType(String connectType) {
      log.info("数据库连接类型【{}】",connectType);
        switch (IngestionType.getVal(connectType)){
            case HIVE:
               return DataTypeEnum.getAllValue();
            case MYSQL:
               return MysqlDataTypeEnum.getAllType();
            default:
               return Map.of();
        }
    }

    @Override
    public Map<String, List<String>> getDataTypeMapping(String sourceConnectType, String targetConnectType) {
        return DataType.TypeMapper.getDataType(sourceConnectType,targetConnectType);
    }
}
