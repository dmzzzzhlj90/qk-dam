package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasReleaseTrendParamsVO;
import org.springframework.stereotype.Service;

/**
 * 数据服务_首页信息
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Service
public interface DasFrontPageInfoService {

    Object releaseTrend(DasReleaseTrendParamsVO dasReleaseTrendParamsVO);

}
