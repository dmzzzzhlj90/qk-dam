package com.qk.dm.dataingestion.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 作业总览
 */
@Builder
@Data
public class DisStatisticsVO {
    /**
     * 当日离线作业
     */
    private String offlineJobCount;
    /**
     * 当日同步作业
     */
    private String syncJobCount;
    /**
     * 异常数
     */
    private String errorCount;
    /**
     * 当日迁移记录
     */
    private String migrationRecord;
    /**
     * 当日同步数据量
     */
    private String syncDataCount;
    /**
     * 失败数
     */
    private String failCount;

}
