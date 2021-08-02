package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import com.qk.dm.metadata.vo.PageResultVO;

import java.util.List;

public interface MtdClassifyAtlasService {
    void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO);

    void update(MtdClassifyAtlasVO mtdClassifyAtlasVO);

    void delete(String ids);

    PageResultVO<MtdClassifyAtlasVO> listByPage(MtdClassifyAtlasVO mtdClassifyAtlasVO);

    List<MtdClassifyAtlasVO> listByAll(MtdClassifyAtlasVO mtdClassifyAtlasVO);
}
