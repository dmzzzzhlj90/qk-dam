package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.service.DasApiCreateSummaryService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 数据服务_新建API公共接口
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Service
public class DasApiCreateSummaryServiceImpl implements DasApiCreateSummaryService {

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;
    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;

    private final DasApiCreateConfigService dasApiCreateConfigService;
    private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;

    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;

    @Autowired
    public DasApiCreateSummaryServiceImpl(DasApiCreateConfigService dasApiCreateConfigService,
                                          DasApiCreateSqlScriptService dasApiCreateSqlScriptService,
                                          DasApiBasicInfoRepository dasApiBasicinfoRepository,
                                          DasApiCreateConfigRepository dasApiCreateConfigRepository,
                                          DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository) {
        this.dasApiCreateConfigService = dasApiCreateConfigService;
        this.dasApiCreateSqlScriptService = dasApiCreateSqlScriptService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
    }

    @Override
    public Object detail(String apiId) {
        Object detailInfo = null;
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo = dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }

        DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
        // 获取新建API配置信息
        Optional<DasApiCreateConfig> onDasApiCreateConfig = dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
        if (onDasApiCreateConfig.isPresent()) {
            detailInfo = dasApiCreateConfigService.detail(dasApiBasicInfo, onDasApiCreateConfig.get());
        } else {
            // 获取新建API SQL脚本方式信息
            Optional<DasApiCreateSqlScript> onDasApiCreateSqlScript = dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));
            if (onDasApiCreateSqlScript.isEmpty()) {
                //入参定义
                DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptService.setDasApiBasicInfoDelInputParam(dasApiBasicInfo);
                return DasApiCreateSqlScriptVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
            }
            detailInfo = dasApiCreateSqlScriptService.detail(dasApiBasicInfo, onDasApiCreateSqlScript.get());
        }

        return detailInfo;
    }

}
