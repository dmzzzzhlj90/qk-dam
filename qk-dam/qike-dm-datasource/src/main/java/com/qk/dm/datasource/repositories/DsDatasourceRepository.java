package com.qk.dm.datasource.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datasource.entity.DsDatasource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DsDatasourceRepository extends BaseRepository<DsDatasource, Integer> {

  @Query(" select linkType from DsDatasource group by linkType")
  List<String> getlinkType();

  @Query(" select dsDatasource from DsDatasource dsDatasource where linkType =:linkType")
  List<DsDatasource> getDsdataSourceByType(@Param("linkType") String linkType);

  @Query(
      " select dsDatasource from DsDatasource dsDatasource where dataSourceName =:dataSourceName")
  List<DsDatasource> getDataSourceByDsname(@Param("dataSourceName") String dataSourceName);

}
