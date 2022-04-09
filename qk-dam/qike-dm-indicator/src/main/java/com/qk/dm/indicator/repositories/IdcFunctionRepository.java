package com.qk.dm.indicator.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.indicator.entity.IdcFunction;
import java.util.List;

public interface IdcFunctionRepository extends BaseRepository<IdcFunction, Long> {

  List<IdcFunction> findAllByEngine(String engine);
}
