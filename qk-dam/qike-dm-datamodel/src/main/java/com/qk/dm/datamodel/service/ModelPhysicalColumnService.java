package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelPhysicalColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalColumnVO;

import java.util.List;

public interface ModelPhysicalColumnService {

    void insert(List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList);

    void update(Long tableId,List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList);

    void delete(String ids);

    List<ModelPhysicalColumnVO> list(Long tableId);

    List<String> queryColumn(String tableName);
}
