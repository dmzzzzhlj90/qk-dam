package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasOpenApiParamsVO;
import org.springframework.stereotype.Service;

/**
 * 导入OpenApi(同步至数据服务操作)
 *
 * @author wjq
 * @date 2021/8/30 17:47
 * @since 1.0.0
 */
@Service
public interface DasSyncOpenApiService {

  int syncRegisterApi(DasOpenApiParamsVO dasOpenApiParamsVO);

}
