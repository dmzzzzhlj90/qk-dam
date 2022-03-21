package com.qk.dm.datasource.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datasource.entity.DsDatasource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DsDatasourceRepository extends BaseRepository<DsDatasource, Integer> {

  @Query(" select linkType from DsDatasource group by linkType")
  List<String> getlinkType();

  @Query(" select dsDatasource from DsDatasource dsDatasource where dsDatasource.dicId=:dicid")
  List<DsDatasource> getByDicId(@Param("dicid") String dicid);

  void deleteById(@Param("dicid") String id);

  Optional<DsDatasource> findById(@Param("dicid") String id);
}
