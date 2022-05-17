package com.qk.dm.datasource.service.impl;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.entity.QDsDatasource;
import com.qk.dm.datasource.mapstruct.mapper.DSDatasourceMapper;
import com.qk.dm.datasource.repositories.DsDatasourceRepository;
import com.qk.dm.datasource.service.DataSourceApiService;
import com.qk.dm.datasource.service.DsDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 数据服务对外提供API接口
 *
 * @author wjq
 * @date 20210826
 * @since 1.0.0
 */
@Service
public class DataSourceApiServiceImpl implements DataSourceApiService {
    private final QDsDatasource qDsDatasource = QDsDatasource.dsDatasource;
    private final DsDataSourceService dsDataSourceService;
    private final DsDatasourceRepository dsDatasourceRepository;

    @Autowired
    public DataSourceApiServiceImpl(
            DsDataSourceService dsDataSourceService, DsDatasourceRepository dsDatasourceRepository) {
        this.dsDataSourceService = dsDataSourceService;
        this.dsDatasourceRepository = dsDatasourceRepository;
    }

    @Override
    public List<String> getAllConnType() {
        return ConnTypeEnum.getConnTypeName();
    }

    @Override
    public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
        List<ResultDatasourceInfo> resultDataList = new ArrayList<>();
        Iterable<DsDatasource> dsDatasourceIterable =
                dsDatasourceRepository.findAll(qDsDatasource.linkType.eq(type));
        for (DsDatasource dsDatasource : dsDatasourceIterable) {
            ResultDatasourceInfo resultDatasourceInfo =
                    DSDatasourceMapper.INSTANCE.useResultDatasourceInfo(dsDatasource);
            resultDatasourceInfo.setDbType(dsDatasource.getLinkType());
            resultDatasourceInfo.setConnectBasicInfoJson(dsDatasource.getDataSourceValues());
            resultDataList.add(resultDatasourceInfo);
        }
        return resultDataList;
    }

    @Override
    public ResultDatasourceInfo getDataSource(String connectName) {
        Optional<DsDatasource> dsDatasourceOptional =
                dsDatasourceRepository.findOne(QDsDatasource.dsDatasource.dataSourceName.eq(connectName));
        if (dsDatasourceOptional.isPresent()) {
            DsDatasource dsDatasource = dsDatasourceOptional.get();
            ResultDatasourceInfo resultDatasourceInfo =
                    DSDatasourceMapper.INSTANCE.useResultDatasourceInfo(dsDatasource);
            resultDatasourceInfo.setDbType(dsDatasource.getLinkType());
            resultDatasourceInfo.setConnectBasicInfoJson(dsDatasource.getDataSourceValues());
            return resultDatasourceInfo;
        }
        return null;
    }

    @Override
    public List<ResultDatasourceInfo> getDataSourceList(List<String> dataSourceNames) {
        List<ResultDatasourceInfo> datasourceInfoList = new ArrayList<>();
        Iterable<DsDatasource> datasourceIterable = dsDatasourceRepository.findAll(QDsDatasource.dsDatasource.dataSourceName.in(dataSourceNames));

        for (DsDatasource dsDatasource : datasourceIterable) {
            ResultDatasourceInfo resultDatasourceInfo = DSDatasourceMapper.INSTANCE.useResultDatasourceInfo(dsDatasource);
            resultDatasourceInfo.setDbType(dsDatasource.getLinkType());
            resultDatasourceInfo.setConnectBasicInfoJson(dsDatasource.getDataSourceValues());
            datasourceInfoList.add(resultDatasourceInfo);
        }

        return datasourceInfoList;
    }

    @Override
    public ResultDatasourceInfo getDataSourceByCode(String dataSourceCode) {
        Optional<DsDatasource> dsDatasourceOptional =
                dsDatasourceRepository.findOne(QDsDatasource.dsDatasource.dataSourceCode.eq(dataSourceCode));
        if (dsDatasourceOptional.isPresent()) {
            DsDatasource dsDatasource = dsDatasourceOptional.get();
            ResultDatasourceInfo resultDatasourceInfo =
                    DSDatasourceMapper.INSTANCE.useResultDatasourceInfo(dsDatasource);
            resultDatasourceInfo.setDbType(dsDatasource.getLinkType());
            resultDatasourceInfo.setConnectBasicInfoJson(dsDatasource.getDataSourceValues());
            return resultDatasourceInfo;
        }
        return null;
    }

    @Override
    public List<ResultDatasourceInfo> getDataSourceListByCode(List<String> dataSourceCodes) {
        List<ResultDatasourceInfo> datasourceInfoList = new ArrayList<>();
        Iterable<DsDatasource> datasourceIterable = dsDatasourceRepository.findAll(QDsDatasource.dsDatasource.dataSourceCode.in(dataSourceCodes));

        for (DsDatasource dsDatasource : datasourceIterable) {
            ResultDatasourceInfo resultDatasourceInfo = DSDatasourceMapper.INSTANCE.useResultDatasourceInfo(dsDatasource);
            resultDatasourceInfo.setDbType(dsDatasource.getLinkType());
            resultDatasourceInfo.setConnectBasicInfoJson(dsDatasource.getDataSourceValues());
            datasourceInfoList.add(resultDatasourceInfo);
        }

        return datasourceInfoList;
    }

}
