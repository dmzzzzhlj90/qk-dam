package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelPhysicalRelationDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalRelationVO;

import java.util.List;

public interface ModelPhysicalRelationService {
    void insert(List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList);

    void update(Long tableId,List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList);

    void delete(String ids);

    List<ModelPhysicalRelationVO> list(Long tableId);
}
