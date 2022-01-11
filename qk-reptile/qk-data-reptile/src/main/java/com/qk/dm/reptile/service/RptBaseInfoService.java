package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface RptBaseInfoService {

    void insert(RptBaseInfoDTO rptBaseInfoDTO);

    void update(Long id, RptBaseInfoDTO rptBaseInfoDTO);

    void updateRunStatus(Long id,Integer runStatus);

    RptBaseInfoVO detail(Long id);

    void delete(String ids);

    PageResultVO<RptBaseInfoVO> listByPage(RptBaseInfoDTO rptBaseInfoDTO, OAuth2AuthorizedClient authorizedClient);

    void updateStatus(Long id,Integer status);

    /**
     * 定时调用爬虫接口
     */
    void timedExecution();

    /**
     * 手动执行调用爬虫接口
     * @param id
     */
    void execution(Long id);

    /**
     * 复制配置
     * @param sourceId
     * @param targetId
     */
    void copyConfig(Long sourceId,Long targetId);
}
