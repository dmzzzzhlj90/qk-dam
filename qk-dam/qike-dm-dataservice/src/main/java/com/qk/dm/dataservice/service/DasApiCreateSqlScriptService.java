package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateSqlScript;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import com.qk.dm.dataservice.vo.DebugApiResultVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据服务_新建API_脚本方式
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Service
public interface DasApiCreateSqlScriptService {

    DasApiCreateSqlScriptVO detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateSqlScript dasApiCreateSqlScript);

    void insert(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);

    void update(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);

    LinkedList<Map<String, Object>> getParamHeaderInfo();

    Object debugModel(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);

    DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(DasApiBasicInfo dasApiBasicInfo);

    List<DasApiCreateSqlScriptDefinitionVO> searchCreateSqlScriptByApiId(List<String> apiIds);

}
