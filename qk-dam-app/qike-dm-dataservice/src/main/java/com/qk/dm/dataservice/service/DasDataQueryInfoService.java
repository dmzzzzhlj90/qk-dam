package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DataQueryInfoVO;

import java.util.List;
/**
 * 提供数据查询服务配置信息
 *
 * @author zhudaoming
 * @since 1.5
 * @date 20220420
 */
public interface DasDataQueryInfoService {
    /**
     * 查询数据查询服务配置信息
     * @return List<DataQueryInfoVO> 聚合类
     */
    List<DataQueryInfoVO> dataQueryInfo();
}
