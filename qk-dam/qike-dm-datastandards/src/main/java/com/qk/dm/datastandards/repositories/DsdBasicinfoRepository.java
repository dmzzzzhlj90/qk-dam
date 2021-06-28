package com.qk.dm.datastandards.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DsdBasicinfoRepository extends BaseRepository<DsdBasicinfo, Integer> {

  @Query("SELECT t FROM DsdBasicinfo t WHERE t.dsdCode in (:codeSet) and t.colName in (:nameSet)")
  List<DsdBasicinfo> findAllByCodeAndName(
      @Param("codeSet") Set<String> codeSet, @Param("nameSet") Set<String> nameSet);
}
