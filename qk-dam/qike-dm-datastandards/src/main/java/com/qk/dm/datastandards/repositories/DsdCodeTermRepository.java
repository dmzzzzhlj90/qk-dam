package com.qk.dm.datastandards.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DsdCodeTermRepository extends BaseRepository<DsdCodeTerm, Integer> {

  @Query("SELECT t FROM DsdCodeTerm t WHERE t.codeId in (:codeSet) and t.codeDirId in (:dirSet)")
  List<DsdCodeTerm> findAllByDirAndCodeId(
      @Param("codeSet") Set<String> codeSet, @Param("dirSet") Set<String> dirSet);
}
