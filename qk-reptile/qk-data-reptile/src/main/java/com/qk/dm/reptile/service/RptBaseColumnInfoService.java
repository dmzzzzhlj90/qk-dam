package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.dto.RptBaseColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseColumnInfoVO;

import java.util.List;

public interface RptBaseColumnInfoService {

    void insert(RptBaseColumnInfoDTO rptBaseColumnInfoDTO);

    void batchInset(Long baseInfoId,List<RptBaseColumnInfoDTO> rptBaseColumnInfoDTOList);

    void update(Long id, RptBaseColumnInfoDTO rptBaseColumnInfoDTO);

    RptBaseColumnInfoVO detail(Long id);

    void delete(Long id);

    List<RptBaseColumnInfoVO> list(Long baseInfoId);

    void deleteByBaseInfoId(Long baseInfoId);

}
