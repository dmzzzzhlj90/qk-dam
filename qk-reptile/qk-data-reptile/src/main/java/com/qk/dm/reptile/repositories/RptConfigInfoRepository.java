package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptConfigInfo;

import java.util.List;

public interface RptConfigInfoRepository extends BaseRepository<RptConfigInfo, Long>{
    List<RptConfigInfo> findAllByBaseInfoIdOrderByIdDesc(Long baseInfoId);

    List<RptConfigInfo> findAllByBaseInfoIdOrderByIdAsc(Long baseInfoId);

    RptConfigInfo findByParentId(Long parentId);

    List<RptConfigInfo> findAllByBaseInfoIdAndParentId(Long baseInfoId,Long parentId);

    void deleteAllByBaseInfoId(Long baseInfoId);
}