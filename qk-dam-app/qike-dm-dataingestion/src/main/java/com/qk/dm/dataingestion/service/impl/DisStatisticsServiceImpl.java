package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.service.DisStatisticsService;
import com.qk.dm.dataingestion.vo.DisStatisticsVO;
import org.springframework.stereotype.Service;
/**
 * 数据引入总览
 * @author wangzp
 * @date 2022/04/25 17:34
 * @since 1.0.0
 */
@Service
public class DisStatisticsServiceImpl implements DisStatisticsService {

    @Override
    public DisStatisticsVO getDetail() {
        //todo 暂时先定义接口格式，前端使用
        return DisStatisticsVO.builder()
                .offlineJobCount("0")
                .migrationRecord("0")
                .errorCount("0")
                .failCount("0")
                .syncDataCount("0")
                .syncJobCount("0").build();
    }
}
