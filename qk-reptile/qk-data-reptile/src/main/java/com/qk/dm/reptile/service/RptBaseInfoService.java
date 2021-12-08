package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;

import java.util.List;

public interface RptBaseInfoService {

    void insert(RptBaseInfoDTO rptBaseInfoDTO);

    void update(Long id, RptBaseInfoDTO rptBaseInfoDTO);

    RptBaseInfoVO detail(Long id);

    void delete(String ids);

    PageResultVO<RptBaseInfoVO> listByPage(RptBaseInfoDTO rptBaseInfoDTO);
}
