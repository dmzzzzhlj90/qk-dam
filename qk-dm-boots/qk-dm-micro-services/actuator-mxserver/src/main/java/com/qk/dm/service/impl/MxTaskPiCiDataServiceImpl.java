package com.qk.dm.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dm.constant.CustomMetricsConstant;
import com.qk.dm.service.MxTaskPiCiDataService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author wjq
 * @date 2021/6/22 17:00
 * @since 1.0.0
 */
@Service
public class MxTaskPiCiDataServiceImpl implements MxTaskPiCiDataService {
    private static final Log LOG = LogFactory.get("异常监控文件");
    private final MeterRegistry meterRegistry;

    @Autowired
    public MxTaskPiCiDataServiceImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void sendTaskPiCiMetrics() {
        LOG.info("开始查询异常数据");
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(LongGovConstant.DATE_TIME_SECOND_PATTERN);
        List<PiciTaskLogVO> piciTaskLogVOList = PiciTaskLogAgg.qkLogPiciAlarms();
        LOG.info("查询到异常数据数量为:【{}】!", piciTaskLogVOList.size());

        LOG.info("开始同步异常数据到prometheus中......");
        piciTaskLogVOList.forEach(
                piciTaskLogVO -> {
                    Integer is_down = piciTaskLogVO.getIs_down();
                    Gauge.builder(CustomMetricsConstant.CUSTOM_TASK_PICI_LOG, is_down, Integer::intValue)
                            .tags(
                                    CustomMetricsConstant.PICI_TASK_TABLENAME,
                                    piciTaskLogVO.getTableName(),
                                    CustomMetricsConstant.PICI_TASK_PICI,
                                    String.valueOf(piciTaskLogVO.getPici()),
                                    CustomMetricsConstant.PICI_TASK_IS_DOWN,
                                    String.valueOf(piciTaskLogVO.getIs_down()),
                                    CustomMetricsConstant.PICI_TASK_IS_ES_UPDATED,
                                    String.valueOf(piciTaskLogVO.getIs_es_updated()),
                                    CustomMetricsConstant.PICI_TASK_IS_MYSQL_UPDATED,
                                    String.valueOf(piciTaskLogVO.getIs_mysql_updated()),
                                    //                            CustomMetricsConstant.PICI_TASK_DOWN_TIME,
                                    // simpleDateFormat.format(piciTaskLogVO.getDown_time()),
                                    CustomMetricsConstant.PICI_TASK_UPDATED,
                                    simpleDateFormat.format(piciTaskLogVO.getUpdated()))
                            .register(meterRegistry);
                    LOG.info("同步表名称为【{}】,批次为:【{}】的异常数据到prometheus中......", piciTaskLogVO.getTableName(), piciTaskLogVO.getPici());
                });
        LOG.info("成功同步异常数据到prometheus中!!!");
    }
}
