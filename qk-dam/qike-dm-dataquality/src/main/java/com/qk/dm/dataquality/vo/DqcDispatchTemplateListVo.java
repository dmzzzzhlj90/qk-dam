package com.qk.dm.dataquality.vo;

import lombok.Data;

@Data
public class DqcDispatchTemplateListVo {

    private Long id;

    /**
     * 模板名称
     */
    private String tempName;

    /**
     * 调度状态 1-调度中 2-运行中 3-停止
     */
    private String dispatchState;

    /**
     * 调度周期 年、月、周、日
     */
    private String dispatchCycle;

    /**
     * 间隔时间
     */
    private String dispatchIntervalTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private String gmtModified;
}
