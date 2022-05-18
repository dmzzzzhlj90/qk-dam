package com.qk.dm.client;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.utils.ConnectInfoConvertUtils;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.vo.AtlasPagination;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
import com.qk.dm.feign.DataSourceFeign;
import com.qk.dm.feign.DataSourceUnifiedFeign;
import com.qk.dm.feign.DataSourceV2Feign;
import com.qk.dm.feign.MetaDataFeign;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据库信息统一Client API
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
@Component
public class DataBaseInfoDefaultApi {

    private final DataSourceFeign dataSourceFeign;
    private final MetaDataFeign metaDataFeign;
    private final DataSourceV2Feign dataSourceV2Feign;
    private final DataSourceUnifiedFeign dataSourceUnifiedFeign;

    public DataBaseInfoDefaultApi(DataSourceFeign dataSourceFeign, MetaDataFeign metaDataFeign,
        DataSourceV2Feign dataSourceV2Feign,
        DataSourceUnifiedFeign dataSourceUnifiedFeign) {
        this.dataSourceFeign = dataSourceFeign;
        this.metaDataFeign = metaDataFeign;
        this.dataSourceV2Feign = dataSourceV2Feign;
        this.dataSourceUnifiedFeign = dataSourceUnifiedFeign;
    }

    // ========================数据源服务_API调用=====================================

    /**
     * 查询所有数据源连接类型
     *
     * @return DefaultCommonResult
     */
    public List<String> getAllConnType() {
        return dataSourceFeign.getAllConnType().getData();
    }

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
        return dataSourceFeign.getResultDataSourceByType(type).getData();
    }

    /**
     * 根据数据源名称获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    public ResultDatasourceInfo getDataSource(String connectName) {
        ResultDatasourceInfo resultDatasourceInfo =
                dataSourceFeign.getDataSource(connectName).getData();
        //todo feign 加判断null
        if (resultDatasourceInfo != null) {
            ConnectBasicInfo connectInfo =
                    ConnectInfoConvertUtils.getConnectInfo(
                            resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
        }
        return resultDatasourceInfo;
    }

    /**
     * 获取数据源集合
     *
     * @return DefaultCommonResult
     */
    public Map<String, ConnectBasicInfo> getDataSourceMap(List<String> dataSourceNames) {
        Map<String, ConnectBasicInfo> dataSourceMap = null;
        try {
            dataSourceMap = Maps.newHashMap();
            List<ResultDatasourceInfo> datasourceInfoList = dataSourceFeign.getDataSourceList(dataSourceNames).getData();
            for (ResultDatasourceInfo resultDatasourceInfo : datasourceInfoList) {
                ConnectBasicInfo connectInfo = ConnectInfoConvertUtils
                        .getConnectInfo(resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
                dataSourceMap.put(resultDatasourceInfo.getDataSourceName(), connectInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dataSourceMap.size() > 0) {
            return dataSourceMap;
        } else {
            throw new BizException("未获取到对应数据源连接信息!!!");
        }
    }

    // ========================元数据服务_API调用=====================================

    /**
     * 获取所有的元数据类型
     *
     * @return DefaultCommonResult<List < MtdAtlasEntityType>>
     */
    public List<MtdAtlasEntityType> getAllEntityType() {
        return metaDataFeign.getAllEntityType().getData();
    }

    /**
     * 获取元数据详情信息
     *
     * @param mtdApiParams
     * @return DefaultCommonResult<MtdApi>
     */
    MtdApi mtdDetail(MtdApiParams mtdApiParams) {
        return metaDataFeign.mtdDetail(mtdApiParams).getData();
    }


    /**
     * 新建API__获取db库信息下拉列表
     *
     * @param type
     * @param server
     * @return
     */
    public List<MtdApiDb> getAllDataBase(String type, String server) {
        return getAllDataBase(type, server, AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);

    }

    /**
     * 新建API__获取table表信息下拉列表
     *
     * @param type,server,dbName
     * @return DefaultCommonResult
     */
    public List<MtdTables> getAllTable(String type, String server, String dbName) {
        return getAllTable(type, server, dbName, AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);

    }

    /**
     * 新建API__获取column字段信息下拉列表
     *
     * @param type,server,dbName
     * @return DefaultCommonResult
     */
    public List getAllColumn(String type, String server, String dbName, String tableName) {
        return getAllColumn(type, server, dbName, tableName, AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);
    }

    /**
     * 通过元数据获取表是否存在和表中是否存在数据
     *
     * @param dbType
     * @param server
     * @param dbName
     * @param tableName
     * @return
     */
    public Integer getExistData(String dbType, String server, String dbName, String tableName) {
        String type = dbType.split("-")[0];
        MtdApiParams build = MtdApiParams.builder().typeName(type + "_table").server(server).dbName(dbName).tableName(tableName).build();
        DefaultCommonResult<Integer> existData = metaDataFeign.getExistData(build);
        return existData.getData();
    }

    public ResultDatasourceInfo getResultDataSourceByCode(String dataSourceCode) {
        return dataSourceFeign.getDataSourceByCode(dataSourceCode).getData();
    }


    /**
     * 根据表的guid获取表的字段信息
     *
     * @param guid
     * @return
     */
    public List getColumnListByTableGuid(String guid) {
        return metaDataFeign.getColumnListByTableGuid(guid).getData();
    }

    /**
     * 新建API__获取db库信息下拉列表
     *
     * @param type
     * @param server
     * @param limit
     * @param offset
     * @return
     */
    public List<MtdApiDb> getAllDataBase(String type, String server, Integer limit, Integer offset) {
        DefaultCommonResult<List<MtdApiDb>> dataBaseList = metaDataFeign.getDataBaseList(
                new MtdDbSearchVO(limit, offset,
                        type + "_db", server));
        return dataBaseList.getData();

    }

    /**
     * 新建API__获取table表信息下拉列表
     *
     * @param type
     * @param server
     * @param dbName
     * @param limit
     * @param offset
     * @return
     */
    public List<MtdTables> getAllTable(String type, String server, String dbName, Integer limit, Integer offset) {
        DefaultCommonResult<List<MtdTables>> tableList = metaDataFeign.getTableList(
                new MtdTableSearchVO(limit, offset,
                        type + "_table", dbName, server));

        return tableList.getData();
    }

    /**
     * 新建API__获取column字段信息下拉列表
     *
     * @param type
     * @param server
     * @param dbName
     * @param tableName
     * @param limit
     * @param offset
     * @return
     */
    public List getAllColumn(String type, String server, String dbName, String tableName, Integer limit, Integer offset) {

        DefaultCommonResult<List<MtdAttributes>> columnList = metaDataFeign.getColumnList(
                new MtdColumnSearchVO(limit, offset,
                        type + "_column", dbName, server, tableName));

        return columnList.getData();
    }

    /**----------------------------------------------------统一获取数据类型、根据数据源连接---------------------------------------------------*/
    /**
     * 获取所有数据源连接类型
     * @return
     */
    public List<String> getUnifiedAllConnType() {
       return dataSourceUnifiedFeign.getAllConnType().getData();
    }

    /**
     *  获取数据源连接（根据数据库类型）
     * @param type 数据库类型
     * @return
     */
    public Map<String,String> getUnifiedResultDataSourceByType( String type){
       return dataSourceUnifiedFeign.getResultDataSourceByType(type).getData();
    }
    /**----------------------------------------------------获取atals库、表信息-------------------------------------------------------------*/

    /**
     * 获取mtd(元数据)中db库信息
     * @param type 数据库类型
     * @param dataSourceCode 数据源connId
     * @return DefaultCommonResult<List<MtdApiDb>> 库信息
     */
    public List<MtdApiDb> getUnifiedAllDataBase( String type,String dataSourceCode) {
        return dataSourceUnifiedFeign.getAllDataBase(type,dataSourceCode).getData();
    }
    /**
     * 获取mtd(元数据)中表信息
     * @param type 数据库类型
     * @param dataSourceCode 数据源标识code编码
     * @param dbName 数据库名称
     * @return DefaultCommonResult<List<MtdTables>> 表信息
     */

    public List<MtdTables> getUnifiedAllTable(String type, String dataSourceCode, String dbName) {
        return dataSourceUnifiedFeign.getAllTable(type,dataSourceCode,dbName).getData();
    }

    /**
     * 获取元数据(mtd)中字段信息
     * @param type 数据库类型
     * @param dataSourceCode 数据源标识code编码
     * @param dbName 数据库名称
     * @param tableName 表名称
     * @return List<MtdAttributes> 字段信息
     */
    public List<MtdAttributes> getUnifiedAllColumn(String type, String dataSourceCode, String dbName, String tableName) {
        return dataSourceUnifiedFeign.getAllColumn(type,dataSourceCode,dbName,tableName).getData();
    }
    /**----------------------------------------------------获取information_schema（直连direct）库、表信息-------------------------------------------------*/
    /**
     * 获取直连(native)中db库信息
     * @param dataSourceCode 数据源标识code编码
     * @return DefaultCommonResult<List<String>> 数据库信息
     */
   public List<String> getUnifiedDctResultDb(String dataSourceCode) {
       return dataSourceUnifiedFeign.getDctResultDb(dataSourceCode).getData();
   }

    /**
     * 获取直连(native)中表信息
     * @param dataSourceCode 数据源标识code编码
     * @param dbName 数据库名称
     * @return DefaultCommonResult<List<String>> 表信息
     */
   public List<String> getUnifiedDctResultTable(String dataSourceCode,String dbName) {
       return dataSourceUnifiedFeign.getDctResultTable(dataSourceCode,dbName).getData();
   }
}
