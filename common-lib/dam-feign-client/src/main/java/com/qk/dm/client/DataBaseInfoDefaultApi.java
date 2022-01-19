package com.qk.dm.client;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.DsDatasourceVO;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.utils.ConnectInfoConvertUtils;
import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.vo.AtlasPagination;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
import com.qk.dm.feign.DataSourceFeign;
import com.qk.dm.feign.DataSourceV2Feign;
import com.qk.dm.feign.DatastandardsFeign;
import com.qk.dm.feign.MetaDataFeign;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    private final DatastandardsFeign datastandardsFeign;

    public DataBaseInfoDefaultApi(DataSourceFeign dataSourceFeign, MetaDataFeign metaDataFeign,
        DataSourceV2Feign dataSourceV2Feign,
        DatastandardsFeign datastandardsFeign) {
        this.dataSourceFeign = dataSourceFeign;
        this.metaDataFeign = metaDataFeign;
        this.dataSourceV2Feign = dataSourceV2Feign;
        this.datastandardsFeign = datastandardsFeign;
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
        Map<String, ConnectBasicInfo> dataSourceMap = new HashMap<>(16);
        List<ResultDatasourceInfo> datasourceInfoList = dataSourceFeign.getDataSourceList(dataSourceNames).getData();

        for (ResultDatasourceInfo resultDatasourceInfo : datasourceInfoList) {
            ConnectBasicInfo connectInfo = ConnectInfoConvertUtils
                    .getConnectInfo(resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
            dataSourceMap.put(resultDatasourceInfo.getDataSourceName(), connectInfo);
        }
        return dataSourceMap;
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
        return getAllDataBase(type,server, AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);

    }

    /**
     * 新建API__获取table表信息下拉列表
     *
     * @param type,server,dbName
     * @return DefaultCommonResult
     */
    public List<MtdTables> getAllTable(String type, String server, String dbName) {
       return  getAllTable(type,server,dbName,AtlasPagination.DEF_LIMIT,AtlasPagination.DEF_OFFSET);

    }

    /**
     * 新建API__获取column字段信息下拉列表
     *
     * @param type,server,dbName
     * @return DefaultCommonResult
     */
    public List getAllColumn(String type, String server, String dbName, String tableName) {
        return getAllColumn(type,server,dbName,tableName,AtlasPagination.DEF_LIMIT,AtlasPagination.DEF_OFFSET);
    }

    /**
     * 通过元数据获取表是否存在和表中是否存在数据
     * @param dbType
     * @param server
     * @param dbName
     * @param tableName
     * @return
     */
    public Integer getExistData(String dbType, String server, String dbName, String tableName){
        String type = dbType.split("-")[0];
        MtdApiParams build = MtdApiParams.builder().typeName(type + "_table").server(server).dbName(dbName).tableName(tableName).build();
        DefaultCommonResult<Integer> existData = metaDataFeign.getExistData(build);
        return existData.getData();
    }

    public List<DsDatasourceVO> getResultDataSourceById(int id) {
        return dataSourceV2Feign.getDataSourceByDsname(id).getData();
    }

    /**
     * 通过条件查询数据标准
     * @param dsdBasicInfoParamsDTO
     * @return
     */
    public PageResultVO<DsdBasicInfoVO> getStandard(DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO) {
        DefaultCommonResult<PageResultVO<DsdBasicInfoVO>> pageResultVODefaultCommonResult = datastandardsFeign.searchList(dsdBasicInfoParamsDTO);
        if (pageResultVODefaultCommonResult !=null){
            return pageResultVODefaultCommonResult.getData();
        }
        return null;
    }

    /**
     * 获取主题数据
     * @return
     */
    public List<DataStandardTreeVO> getTree() {
       return datastandardsFeign.searchList().getData();
    }

    /**
     * 根据表的guid获取表的字段信息
     * @param guid
     * @return
     */
    public List getColumnListByTableGuid(String guid){
        return metaDataFeign.getColumnListByTableGuid(guid).getData();
    }

    /**
     * 新建API__获取db库信息下拉列表
     * @param type
     * @param server
     * @param limit
     * @param offset
     * @return
     */
    public List<MtdApiDb> getAllDataBase(String type, String server,Integer limit,Integer offset) {
        DefaultCommonResult<List<MtdApiDb>> dataBaseList = metaDataFeign.getDataBaseList(
                new MtdDbSearchVO(limit, offset,
                        type + "_db", server));
        return dataBaseList.getData();

    }

    /**
     * 新建API__获取table表信息下拉列表
     * @param type
     * @param server
     * @param dbName
     * @param limit
     * @param offset
     * @return
     */
    public List<MtdTables> getAllTable(String type, String server, String dbName,Integer limit,Integer offset) {
        DefaultCommonResult<List<MtdTables>> tableList = metaDataFeign.getTableList(
                new MtdTableSearchVO(limit, offset,
                        type + "_table", dbName, server));

        return tableList.getData();
    }

    /**
     * 新建API__获取column字段信息下拉列表
     * @param type
     * @param server
     * @param dbName
     * @param tableName
     * @param limit
     * @param offset
     * @return
     */
    public List getAllColumn(String type, String server, String dbName, String tableName,Integer limit,Integer offset) {

        DefaultCommonResult<List<MtdAttributes>> columnList = metaDataFeign.getColumnList(
                new MtdColumnSearchVO(limit, offset,
                        type + "_column", dbName, server, tableName));

        return columnList.getData();
    }

}
