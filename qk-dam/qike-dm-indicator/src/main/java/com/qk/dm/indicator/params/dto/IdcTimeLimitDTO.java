package com.qk.dm.indicator.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcTimeLimitDTO {

    /**
     * 限定名称
     */
    private String limitName;

    /**
     * 限定类型 1按分钟 2按小时 3按月 4按年
     */
    private Integer limitType;

    /**
     * 基准时间 如limit_type按月表示每月多少日，如limit_type按日表示每日多少时，如limit_type按小时表示每时的多少分
     */
    private String baseTime;

    /**
     * 快速选择
     */
    private String quickStart;

    /**
     * 开始位置
     */
    private String start;

    /**
     * 结束位置
     */
    private String end;

    /**
     * 描述信息
     */
    private String describe;

}
