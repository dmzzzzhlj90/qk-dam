package com.qk.dm.dataquality.constant;

import com.alibaba.druid.DbType;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算引擎类型
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum EngineTypeEnum {
    MYSQL( "MYSQL", DbType.mysql),
    HIVE( "HIVE", DbType.hive),
    ORACLE( "ORACLE", DbType.oracle),
    ELASTICSEARCH( "ELASTICSEARCH", DbType.elastic_search);

    private String code;
    private DbType dbType;

    EngineTypeEnum(String code, DbType dbType) {
        this.code = code;
        this.dbType = dbType;
    }

    public static EngineTypeEnum fromValue(String code) {
        if (!ObjectUtils.isEmpty(code)) {
            for (EngineTypeEnum b : EngineTypeEnum.values()) {
                if (b.code.equals(code)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (EngineTypeEnum enums : EngineTypeEnum.values()) {
            val.put(enums.code, enums.code);
        }
        return val;
    }

    public String getCode() {
        return code;
    }

    public DbType getDbType() {
        return dbType;
    }
}
