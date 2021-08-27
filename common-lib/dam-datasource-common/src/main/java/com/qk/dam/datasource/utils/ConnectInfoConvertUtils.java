package com.qk.dam.datasource.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.datasource.entity.*;
import com.qk.dam.datasource.enums.ConnTypeEnum;

/**
 * @author wjq
 * @date 2021/8/26 17:27
 * @since 1.0.0
 */
public class ConnectInfoConvertUtils {
    public final static ObjectMapper objectMapper = new ObjectMapper();

    public static ConnectBasicInfo getConnectInfo(String type, String connectBasicInfoJson) {
        ConnectBasicInfo connectBasicInfo = null;
        try {
            connectBasicInfo = null;
            if (type.equalsIgnoreCase(ConnTypeEnum.MYSQL.getName())) {
                return objectMapper.readValue(connectBasicInfoJson, MysqlInfo.class);
            }
            if (type.equalsIgnoreCase(ConnTypeEnum.HIVE.getName())) {
                return objectMapper.readValue(connectBasicInfoJson, HiveInfo.class);
            }
            if (type.equalsIgnoreCase(ConnTypeEnum.ORACLE.getName())) {
                return objectMapper.readValue(connectBasicInfoJson, OracleInfo.class);
            }
            if (type.equalsIgnoreCase(ConnTypeEnum.POSTGRESQL.getName())) {
                return objectMapper.readValue(connectBasicInfoJson, PostgresqlInfo.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return connectBasicInfo;
    }
}
