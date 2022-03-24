package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableColumnVO;

import java.util.List;

public interface ModelDimTableColumnService {
    void insert(List<ModelDimTableColumnDTO> modelDimTableColumnDTOList);

    ModelDimTableColumnVO detail(Long id);

    void update(Long dimTableId,List<ModelDimTableColumnDTO> modelDimTableColumnDTOList);

    void delete(String dimTableIds);

    List<ModelDimTableColumnVO> list(Long dimTableId);

    void deleteByDimTableId(Long dimTableId);
}
