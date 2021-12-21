package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;

import java.util.List;

public interface RptSelectorColumnInfoService {

    void insert(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO);

    void batchInset(List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList);

    void update(Long id, RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO);

    void batchUpdate(Long configId,List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList);

    RptSelectorColumnInfoVO detail(Long id);

    void delete(String ids);

    void deleteByConfigId(Long configId);

    List<RptSelectorColumnInfoVO> list(Long configId);
    /**
     * 复制配置项
     */
    void copyConfig(Long sourceId,Long targetId);
}
