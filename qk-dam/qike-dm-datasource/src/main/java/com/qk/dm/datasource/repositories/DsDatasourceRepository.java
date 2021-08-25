package com.qk.dm.datasource.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datasource.entity.DsDatasource;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DsDatasourceRepository extends BaseRepository<DsDatasource, Integer> {

  @Query(" select linkType from DsDatasource group by linkType")
  List<String> getlinkType();
}
