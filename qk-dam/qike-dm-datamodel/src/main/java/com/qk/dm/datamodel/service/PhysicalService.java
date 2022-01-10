package com.qk.dm.datamodel.service;

import com.qk.dam.entity.DataStandardInfoVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelPhysicalDTO;
import com.qk.dm.datamodel.params.dto.ModelReverseBaseDTO;
import com.qk.dm.datamodel.params.dto.QueryModelPhysicalDTO;
import com.qk.dm.datamodel.params.vo.CensusDataVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 关系建模server
 */
@Service
public interface PhysicalService {

  void insert(ModelPhysicalDTO modelPhysicalDTO);

  void delete(List<Long> ids);

  void update(ModelPhysicalDTO modelPhysicalDTO);

  PageResultVO<ModelPhysicalVO> query(QueryModelPhysicalDTO queryModelPhysicalDTO);

  ModelPhysicalTableVO getModelPhysical(Long id);

  CensusDataVO getCensusData(QueryModelPhysicalDTO queryModelPhysicalDTO);

  List<Map<String, String>> getDataTypes();

  String getSql(Long tableId);

  void synchronization(List<Long> physicalIds);

  void push(List<Long> idList);

  void reverseBase(ModelReverseBaseDTO modelReverseBaseDTO);

  List<String> queryDataType();

  List<DataStandardInfoVO> getTree();
}
