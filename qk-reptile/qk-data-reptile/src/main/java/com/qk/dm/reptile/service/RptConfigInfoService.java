package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptAddConfigVO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;

import java.util.List;

public interface RptConfigInfoService {

    RptAddConfigVO insert(RptConfigInfoDTO rptConfigInfoDTO);

    Long endAndStart(RptConfigInfoDTO rptConfigInfoDTO);

    void update(Long id, RptConfigInfoDTO rptConfigInfoDTO);

    RptConfigInfoVO detail(Long id);

    void delete(String ids);

    List<RptConfigInfoVO> list(Long baseInfoId);

    /**
     * 调用爬虫接口使用，转换headers、cookie等，按照id正序排列
     * @param baseId
     * @return
     */
    List<RptConfigInfoVO> rptList(Long baseId);
}
