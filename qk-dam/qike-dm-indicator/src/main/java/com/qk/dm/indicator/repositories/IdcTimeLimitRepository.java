package com.qk.dm.indicator.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.indicator.entity.IdcTimeLimit;
import java.util.List;

public interface IdcTimeLimitRepository extends BaseRepository<IdcTimeLimit, Long> {

  List<IdcTimeLimit> findAllByDelFlag(Integer delFlag);
}
