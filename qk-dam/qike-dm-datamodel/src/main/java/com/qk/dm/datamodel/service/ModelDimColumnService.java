package com.qk.dm.datamodel.service;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimColumnVO;

import java.util.List;

public interface ModelDimColumnService {
    void insert(List<ModelDimColumnDTO> modelDimColumnDTOList);

    void update(Long dimId,List<ModelDimColumnDTO> modelPhysicalColumnDTOList);

    void delete(String ids);

    List<ModelDimColumnVO> list(Long dimId);
}
