package com.qk.dm.dataquality.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.biz.CacheBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
@Service
@Slf4j
public class DqcStatisticsServiceImpl implements DqcStatisticsService {
    private final CacheBiz cacheBiz;

    public DqcStatisticsServiceImpl(CacheBiz cacheBiz) {
        this.cacheBiz = cacheBiz;
    }

    @Override
    public DataSummaryVO summary() {
        return GsonUtil.fromJsonString(cacheBiz.summary(), new TypeToken<DataSummaryVO>() {}.getType());
    }

    @Override
    public List<DimensionVO> dimension() {
        return GsonUtil.fromJsonString(cacheBiz.dimension(), new TypeToken<List<DimensionVO>>() {}.getType());
    }

    @Override
    public List<RuleDirVO> dir() {
        return GsonUtil.fromJsonString(cacheBiz.dir(), new TypeToken<List<RuleDirVO>>() {}.getType());
    }

    @Override
    public List<DqcProcessInstanceVO> instanceList() {
        return cacheBiz.instanceList();
    }
}
