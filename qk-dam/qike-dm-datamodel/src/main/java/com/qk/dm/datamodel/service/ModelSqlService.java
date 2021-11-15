package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelSqlVO;


public interface ModelSqlService {

    void insert(ModelSqlDTO modelDTO);

    ModelSqlVO detail(Long id);

    void update(Long tableId,ModelSqlDTO modelDTO);

    void delete(Long tableId);

}
