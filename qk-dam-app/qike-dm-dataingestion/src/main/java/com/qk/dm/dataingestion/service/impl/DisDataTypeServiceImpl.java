package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.enums.DataTypeMapping;
import com.qk.dm.dataingestion.enums.HiveDataType;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.enums.MysqlDataType;
import com.qk.dm.dataingestion.service.DisDataTypeService;
import com.qk.dm.dataingestion.vo.ColumnVO;
import com.qk.dm.dataingestion.vo.DataTypeCheckVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
               return HiveDataType.getAllType();
            case MYSQL:
               return MysqlDataType.getAllType();
            default:
               return Map.of();
        }
    }

    @Override
    public Map<String, List<String>> getDataTypeMapping(String sourceConnectType, String targetConnectType) {
        return DataTypeMapping.getDataType(sourceConnectType,targetConnectType);
    }

    @Override
    public DataTypeCheckVO checkDataType(DataTypeCheckVO dataTypeCheckVO) {
        DataTypeCheckVO.DataType source = dataTypeCheckVO.getSource();
        DataTypeCheckVO.DataType sourceCheck = DataTypeCheckVO.DataType.builder()
                .connectType(source.getConnectType())
                .columnList(List.of()).build();

        DataTypeCheckVO.DataType target = dataTypeCheckVO.getTarget();
        DataTypeCheckVO.DataType targetCheck = DataTypeCheckVO.DataType.builder()
                .connectType(target.getConnectType())
                .columnList(errorList(target.getConnectType(), target.getColumnList())).build();

        return DataTypeCheckVO.builder()
                .source(sourceCheck)
                .target(targetCheck)
                .build();
    }

    /**
     * 错误的数据类型字段列表
     * @param columnList
     * @return
     */
    private List<ColumnVO.Column> errorList(String connectType,List<ColumnVO.Column> columnList){

            switch (IngestionType.getVal(connectType)){
                case MYSQL:
                  return  MysqlDataType.checkColumn(columnList);
                case HIVE:
                    return HiveDataType.checkColumn(columnList);
                default:
                    return List.of();
            }


    }
}

