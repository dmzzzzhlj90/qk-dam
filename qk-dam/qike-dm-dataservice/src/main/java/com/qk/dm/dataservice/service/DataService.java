package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DataPushVO;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 13:56
 */
public interface DataService {
    /**
     * 处理数据
     * @param dataPushVO
     * @return
     */
    String dataPush(DataPushVO dataPushVO);
}
