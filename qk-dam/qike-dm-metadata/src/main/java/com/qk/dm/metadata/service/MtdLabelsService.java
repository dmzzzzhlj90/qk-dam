package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;

import java.util.List;

public interface MtdLabelsService {

    void insert(MtdLabelsVO mtdLabelsVO);

    void update(MtdLabelsVO mtdLabelsVO);

    void delete(String ids);

    PageResultVO<MtdLabelsVO> listByPage(MtdLabelsListVO mtdLabelsListVO);

    List<MtdLabelsVO> listByAll(MtdLabelsListVO mtdLabelsListVO);
}
