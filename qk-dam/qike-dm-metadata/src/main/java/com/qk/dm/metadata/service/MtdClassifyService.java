package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;

import java.util.List;

public interface MtdClassifyService {

    void insert(MtdClassifyVO mtdClassifyVO);

    void update(MtdClassifyVO mtdClassifyVO);

    void delete(String ids);

    PageResultVO<MtdClassifyVO> listByPage(MtdClassifyVO mtdClassifyVO);

    List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO);
}
