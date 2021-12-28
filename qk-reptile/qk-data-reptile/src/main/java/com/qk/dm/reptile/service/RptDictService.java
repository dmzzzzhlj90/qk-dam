package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.vo.RptDictVO;

import java.util.List;

public interface RptDictService {

    List<RptDictVO> getDictList(Long pid);
}
