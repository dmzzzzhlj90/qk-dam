package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DebugApiParasVO;
import com.qk.dm.dataservice.vo.DebugApiResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据服务_新建API公共接口
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Service
public interface DasApiCreateSummaryService {

    Object detail(String apiId);

    Object remoteSearchData(String apiId, String paramData);

}
