package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptBaseInfo;

import java.util.List;

public interface RptBaseInfoRepository extends BaseRepository<RptBaseInfo, Long> {
        List<RptBaseInfo> findAllByStatus(Integer status);

        List<RptBaseInfo> findAllByRunStatusAndStatusAndTimeIntervalAndDelFlag(Integer runStatus, Integer status,String timeInterval,Integer delFlag);
}