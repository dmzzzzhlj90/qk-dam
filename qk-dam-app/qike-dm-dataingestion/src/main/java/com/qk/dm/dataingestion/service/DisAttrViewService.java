package com.qk.dm.dataingestion.service;

import com.qk.dm.dataingestion.vo.DisAttrViewVO;
import com.qk.dm.dataingestion.vo.DisViewParamsVO;

import java.util.List;

public interface DisAttrViewService {
    List<DisAttrViewVO> list(DisViewParamsVO vo);
}
