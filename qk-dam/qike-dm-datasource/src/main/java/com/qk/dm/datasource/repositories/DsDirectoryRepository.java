package com.qk.dm.datasource.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datasource.entity.DsDirectory;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DsDirectoryRepository extends BaseRepository<DsDirectory, Integer> {
  @Query(" select sysName from DsDirectory group by sysName")
  List<String> getSysName();
}
