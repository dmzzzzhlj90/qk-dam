package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import java.util.Map;
import org.springframework.stereotype.Service;

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
}
