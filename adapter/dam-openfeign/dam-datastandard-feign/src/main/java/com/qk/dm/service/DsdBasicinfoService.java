package com.qk.dm.service;

import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicinfoParamsVO;
import com.qk.dam.jpa.pojo.PageResultVO;

import java.util.List;

public interface DsdBasicinfoService {
    /**
     * 根据条件查询数据标准
     * @param dsdBasicInfoParamsDTO
     * @return
     */
    PageResultVO<DsdBasicinfoParamsVO> getStandard(DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO);

    /**
     * 查询主题
     * @return
     */
    List<DataStandardTreeVO> getTree();
}
