package com.qk.dm.datasource.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectInfoVo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dam.metedata.vo.AtlasPagination;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
import com.qk.dm.datasource.constant.DsConstant;
import com.qk.dm.datasource.service.DataSourceApiService;
import com.qk.dm.datasource.service.DsUnifiedApiService;
import com.qk.dm.datasource.service.MetadataApiService;
import com.qk.dm.feign.MetaDataFeign;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用数据源连接service
 *
 * @author zys
 * @date 2022/4/29 15:22
 * @since 1.0.0
 */
@Service
public class DsUnifiedApiServiceImpl implements DsUnifiedApiService {
    private final DataSourceApiService dataSourceApiService;
    private final MetadataApiService metadataApiService;
    private final MetaDataFeign metaDataFeign;

    public DsUnifiedApiServiceImpl(DataSourceApiService dataSourceApiService,
                                   MetadataApiService metadataApiService,
                                   MetaDataFeign metaDataFeign) {
        this.dataSourceApiService = dataSourceApiService;
        this.metadataApiService = metadataApiService;
        this.metaDataFeign = metaDataFeign;
    }

    @Override
    public List<String> getAllConnType() {
        return dataSourceApiService.getAllConnType();
    }

    @Override
    public Map<String, String> getAllDataSourcesByType(String engineType) {
        List<ResultDatasourceInfo> resultDataSourceByType = dataSourceApiService.getResultDataSourceByType(engineType);
        return resultDataSourceByType
                .stream()
                .collect(Collectors.toMap(ResultDatasourceInfo::getDataSourceCode, ResultDatasourceInfo::getDataSourceName));
    }

    @Override
    public List<MtdApiDb> getAllDataBase(String engineType, String dataSourceCode) {
        ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
        if (Objects.nonNull(dataSourceByConnId)) {
            MtdDbSearchVO mtdDbSearchVO =
                    new MtdDbSearchVO(
                            AtlasPagination.DEF_LIMIT,
                            AtlasPagination.DEF_OFFSET,
                            getDbTypeName(engineType),
                            getService(dataSourceByConnId));
            return metaDataFeign.getDataBaseList(mtdDbSearchVO).getData();
        }
        throw new BizException("dataSourceCode为" + dataSourceCode + "的数据源为空");
    }

    @Override
    public List<MtdTables> getAllTable(String engineType, String dataSourceCode, String dataBaseName) {
        ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
        if (Objects.nonNull(dataSourceByConnId)) {
            MtdTableSearchVO mtdTableSearchVO =
                    new MtdTableSearchVO(
                            AtlasPagination.DEF_LIMIT,
                            AtlasPagination.DEF_OFFSET,
                            getTableTypeName(engineType),
                            dataBaseName,
                            getService(dataSourceByConnId));
            return metaDataFeign.getTableList(mtdTableSearchVO).getData();
        }
        throw new BizException("dataSourceCode为" + dataSourceCode + "的数据源为空");
    }

    @Override
    public List<MtdAttributes> getAllColumn(String engineType, String dataSourceCode, String dataBaseName, String tableName) {
        ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
        if (Objects.nonNull(dataSourceByConnId)) {
            MtdColumnSearchVO mtdColumnSearchVO = new MtdColumnSearchVO(
                    AtlasPagination.DEF_LIMIT,
                    AtlasPagination.DEF_OFFSET,
                    getTypeName(engineType),
                    dataBaseName,
                    getService(dataSourceByConnId),
                    tableName);
            return metaDataFeign.getColumnList(mtdColumnSearchVO).getData();
        }
        throw new BizException("dataSourceCode为" + dataSourceCode + "的数据源为空");
    }

    @Override
    public List<String> getDctResultDb(String dataSourceCode) {
        ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
        if (Objects.nonNull(dataSourceByConnId)) {
            ConnectInfoVo connectInfoVo =
                    GsonUtil.fromJsonString(dataSourceByConnId.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {
                    }.getType());
            return metadataApiService.queryDB(connectInfoVo);
        } else {
            throw new BizException("dataSourceCode为" + dataSourceCode + "的数据源为空");
        }
    }

    @Override
    public List<String> getDctResultTable(String dataSourceCode,
                                          String databaseName) {
        ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
        if (Objects.nonNull(dataSourceByConnId)) {
            ConnectInfoVo connectInfoVo =
                    GsonUtil.fromJsonString(dataSourceByConnId.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {
                    }.getType());
            connectInfoVo.setDatabaseName(databaseName);
            return metadataApiService.queryTable(connectInfoVo);
        } else {
            throw new BizException("根据连接名称获取连接信息失败");
        }
    }

    private String getService(ResultDatasourceInfo dataSourceCode) {
        Map<String, String> map =
                GsonUtil.fromJsonString(dataSourceCode.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {}.getType());
        return map.get(DsConstant.SERVER);
    }

    private String getDbTypeName(String engineType) {
        return engineType + DsConstant.DB_NAME;
    }

    private String getTableTypeName(String engineType) {
        return engineType + DsConstant.TABLE_NAME;
    }

    private String getTypeName(String engineType) {
        return engineType + DsConstant.COLUMN_NAME;
    }
}