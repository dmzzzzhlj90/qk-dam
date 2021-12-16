package com.qk.dm.service;

import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
public interface DataBaseService {

    /**
     * 获取数据源连接类型
     * @return
     */
    List<String> getAllConnectType();

    /**
     * 获取数据源连接
     * @param connectType
     * @return
     */
    List<String> getAllDataSource(String connectType);

    /**
     * 获取db库列表
     * @param connectType
     * @param dataSourceName
     * @return
     */
    List<MtdApiDb> getAllDataBase(String connectType, String dataSourceName);

    /**
     * 获取table表列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @return
     */
    List<MtdTables> getAllTable(String connectType, String dataSourceName, String dataBaseName);

    /**
     * 获取column字段列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param tableName
     * @return
     */
    List getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName);
}
