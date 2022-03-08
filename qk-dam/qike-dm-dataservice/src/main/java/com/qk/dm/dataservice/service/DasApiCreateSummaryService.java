package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

}
