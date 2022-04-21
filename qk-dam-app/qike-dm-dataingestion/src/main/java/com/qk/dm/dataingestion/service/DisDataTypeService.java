package com.qk.dm.dataingestion.service;

import java.util.Map;

public interface DisDataTypeService {
    /**
     * 根据连接类型获取对应的数据类型
     * @param connectType 连接类型
     * @return Map<String, String>
     */
    Map<String, String> getDataType(String  connectType);

    /**
     * 获取字段类型映射
     * @param connectType 连接类型
     * @param dataType 数据类型
     * @return Map<String, String> 对应的mysql或hive的数据类型
     */
    Map<String, String> getDataTypeMapping(String connectType,String dataType);

}
