package com.qk.dm;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;
import java.util.Map;

/**
 * 内部调用-准备废弃（能不用此类尽量别用）
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
public interface DataBaseService {

    List<ResultDatasourceInfo> getResultDataSourceByType(String type);

    List<String> getAllConnType();

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

    /**
     * 通过表guid获取表字段信息
     * @param tableGuid
     * @return
     */
    List getColumnListByTableGuid(String tableGuid);
    /**
     * 获取db库列表
     * @param connectType
     * @param dataSourceName
     * @param limit
     * @param offset
     * @return
     */
    List<MtdApiDb> getAllDataBase(String connectType, String dataSourceName,Integer limit,Integer offset);

    /**
     * 获取table表列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param limit
     * @param offset
     * @return
     */
    List<MtdTables> getAllTable(String connectType, String dataSourceName, String dataBaseName,Integer limit,Integer offset);

    /**
     * 获取column字段列表
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param tableName
     * @param limit
     * @param offset
     * @return
     */
    List getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName,Integer limit,Integer offset);
    /**
     * 通过元数据获取表是否存在和表中是否存在数据
     * @param connectType
     * @param dataSourceName
     * @param dataBaseName
     * @param tableName
     * @return
     */
    Integer getExistData(String connectType, String dataSourceName, String dataBaseName, String tableName);
    /**
     * 获取数据源连接名称，id
     * @return
     */
    Map<String,String> getAllDataSources(String connectType);



}
