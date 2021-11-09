package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelPhysicalTableDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;

public interface ModelPhysicalTableService {

  void insert(ModelPhysicalTableDTO modelPhysicalTableDTO);

  ModelPhysicalTableVO detail(Long id);

  void update(Long id,ModelPhysicalTableDTO modelPhysicalTableDTO);

  void delete(String ids);

  PageResultVO<ModelPhysicalTableVO> listPage(ModelPhysicalTableDTO modelPhysicalTableDTO);



}
