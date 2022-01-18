package com.qk.dm.service;

import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicinfoParamsVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;
import java.util.Map;

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
    Map<Integer,String> getAllDataSources(String connectType);

    /**
     * 根据id获取数据源连接
     * @param id
     * @return
     */
    String getResultDataSourceByid(int id);

  /**
   * 根据条件查询数据标准
   * @param dsdBasicInfoParamsDTO
   * @return
   */
  PageResultVO<DsdBasicinfoParamsVO> getStandard(DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO);

  List<DataStandardTreeVO> getTree();
}
