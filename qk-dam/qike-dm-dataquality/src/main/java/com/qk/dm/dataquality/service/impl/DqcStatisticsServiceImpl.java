package com.qk.dm.dataquality.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.vo.statistics.handler.CacheHandler;
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
    private final CacheHandler cacheHandler;

    public DqcStatisticsServiceImpl(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    @Override
    public DataSummaryVO summary() {
//        log.info("===== summary定时开始执行 =====");
        return GsonUtil.fromJsonString(cacheHandler.summary(), new TypeToken<DataSummaryVO>() {}.getType());
    }

    @Override
    public List<DimensionVO> dimension() {
//        log.info("===== dimension定时开始执行 =====");
        return GsonUtil.fromJsonString(cacheHandler.dimension(), new TypeToken<List<DimensionVO>>() {}.getType());
    }

    @Override
    public List<RuleDirVO> dir() {
//        log.info("===== dir定时开始执行 =====");
        return GsonUtil.fromJsonString(cacheHandler.dir(), new TypeToken<List<RuleDirVO>>() {}.getType());
    }
}
