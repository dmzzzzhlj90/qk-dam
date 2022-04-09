package com.qk.dm.reptile.params.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 定时时间间隔
 */
@Data
@Builder
public class TimeIntervalVO {
    private String name;
    private String desc;
}
