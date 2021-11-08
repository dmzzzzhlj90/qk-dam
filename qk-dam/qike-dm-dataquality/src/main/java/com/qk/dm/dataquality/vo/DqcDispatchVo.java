package com.qk.dm.dataquality.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class DqcDispatchVo {

    /**
     * 调度模版id
     */
    @NotNull(message = "调度模版id不能为空！")
    private Long dispatchTempId;

    /**
     * 调度执行方式 1-手动执行 2-调度执行
     */
    @NotBlank(message = "调度执行方式不能为空！")
    private String runType;

    /**
     * 调度周期 年、月、周、日
     */
    private String dispatchCycle;

    /**
     * 周期开始时间
     */
    private String dispatchTimeStart;

    /**
     * 周期结束时间
     */
    private String dispatchTimeEnt;

    /**
     * 间隔时间
     */
    private String dispatchIntervalTime;

    /**
     * 调度执行cron表达式
     */
    private String cron;
}
