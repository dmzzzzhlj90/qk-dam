package com.qk.dm.dataingestion.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataingestion.entity.DisAttrView;
import java.util.List;

public interface DisColumnViewRepository extends BaseRepository<DisAttrView, Long> {

 List<DisAttrView> findAllByTypeAndConnectType(String type,String connectType);
}