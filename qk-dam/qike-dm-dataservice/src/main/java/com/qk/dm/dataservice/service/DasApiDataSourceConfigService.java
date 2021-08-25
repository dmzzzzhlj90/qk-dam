package com.qk.dm.dataservice.service;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Service
public interface DasApiDataSourceConfigService {

    DasApiDataSourceConfigVO getDasDataSourceConfigInfoByApiId(String apiId);

    void addDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO);

    void updateDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO);

    Map<String, String> getDSConfigRequestParaHeaderInfo();

    Map<String, String> getDSConfigResponseParaHeaderInfo();

    Map<String, String> getDSConfigOrderParaHeaderInfo();

    List<String> getDSConfigParasCompareSymbol();

    Map<String, String> getDSConfigParasSortStyle();

    List<String> getAllConnType();

    DefaultCommonResult getDataSourceByType(String type);
}
