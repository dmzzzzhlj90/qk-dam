package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;

public interface RptBaseInfoService {

    void insert(RptBaseInfoDTO rptBaseInfoDTO);

    void update(Long id, RptBaseInfoDTO rptBaseInfoDTO);

    RptBaseInfoVO detail(Long id);

    void delete(String ids);

    PageResultVO<RptBaseInfoVO> listByPage(RptBaseInfoDTO rptBaseInfoDTO);

    void updateStatus(Long id,Integer status);

    /**
     * 定时调用爬虫接口
     */
    void timedExecution();

    void execution(Long id);
}
