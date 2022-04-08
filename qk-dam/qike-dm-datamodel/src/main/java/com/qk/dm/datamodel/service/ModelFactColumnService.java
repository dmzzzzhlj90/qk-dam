package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelFactColumnVO;

import java.util.List;

public interface ModelFactColumnService {
    void insert(List<ModelFactColumnDTO> modelFactColumnDTOList);

    void update(Long factId,List<ModelFactColumnDTO> modelFactColumnDTOList);

    void delete(String factIds);

    List<ModelFactColumnVO> list(Long factId);
}
