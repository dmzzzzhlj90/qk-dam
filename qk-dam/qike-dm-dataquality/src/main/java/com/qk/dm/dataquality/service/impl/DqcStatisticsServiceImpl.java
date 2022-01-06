package com.qk.dm.dataquality.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.vo.statistics.handler.CacheHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
@Service
public class DqcStatisticsServiceImpl implements DqcStatisticsService {
    private final CacheHandler cacheHandler;

    public DqcStatisticsServiceImpl(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    @Override
    public void timeToReis() {
        try {
            //统计总揽
            cacheHandler.summary();
            //纬度统计
            cacheHandler.dimension();
            //分类统计
            cacheHandler.dir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataSummaryVO summary() {
        return GsonUtil.fromJsonString(cacheHandler.summary(), new TypeToken<DataSummaryVO>() {}.getType());
    }

    @Override
    public List<DimensionVO> dimension() {
        return GsonUtil.fromJsonString(cacheHandler.dimension(), new TypeToken<List<DimensionVO>>() {}.getType());
    }

    @Override
    public List<RuleDirVO> dir() {
        return GsonUtil.fromJsonString(cacheHandler.dir(), new TypeToken<List<RuleDirVO>>() {}.getType());
    }
}
