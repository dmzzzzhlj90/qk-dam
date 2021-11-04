package com.qk.dm.metadata.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import java.util.List;

public interface MtdClassifyAtlasRepository extends BaseRepository<MtdClassifyAtlas, Long> {
  List<MtdClassifyAtlas> findAllBySynchStatusInOrderByGmtCreateAsc(List<Integer> synchStatus);

  List<MtdClassifyAtlas> findAllBySynchStatusNotOrderByGmtCreateAsc(Integer status);

  List<MtdClassifyAtlas> findAllBySynchStatusNot(Integer status);

  MtdClassifyAtlas findByGuid(String guid);

  List<MtdClassifyAtlas> findAllByGuidIn(List<String> guids);
}
