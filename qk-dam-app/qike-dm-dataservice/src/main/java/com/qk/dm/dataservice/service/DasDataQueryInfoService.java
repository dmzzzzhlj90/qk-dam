package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateMybatisSqlScript;
import com.qk.dm.dataservice.vo.DasApiCreateResponseParasVO;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 提供数据查询服务配置信息
 *
 * @author zhudaoming
 * @date 20220420
 * @since 1.5
 */
public interface DasDataQueryInfoService {
    /**
     * 查询数据查询服务配置信息
     *
     * @return List<DataQueryInfoVO> 聚合类
     */
    List<DataQueryInfoVO> dataQueryInfo();

    List<DataQueryInfoVO> dataQueryInfo(Long id);

    List<DataQueryInfoVO> dataQueryInfoLast(Long id);

    void insert(DataQueryInfoVO dataQueryInfoVO);

    void update(DataQueryInfoVO dataQueryInfoVO);

    List<DasApiCreateResponseParasVO> generateResponseParam(String sqlPara);

    Object detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateMybatisSqlScript apiCreateMybatisSqlScript);

    LinkedList<Map<String, Object>> paramHeaderInfo();

    LinkedList<Map<String, Object>> responseParamHeaderInfo();

    LinkedList<Map<String, Object>> orderParamHeaderInfo();

    Map<Boolean, String> pageFlags();

    Map<Integer, String> cacheLevels();

}
