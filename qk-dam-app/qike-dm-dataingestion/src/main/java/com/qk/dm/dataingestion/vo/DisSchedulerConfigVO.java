package com.qk.dm.dataingestion.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisSchedulerConfigVO {

    private Long id;

    /**
     * 迁移基础表id
     */
    private Long baseInfoId;

    /**
     * 定时是否开启
     */
    private Boolean timeSwitch;

    /**
     * 调度执行方式 "SCHEDULER_TYPE_SINGLE":"单次调度" "SCHEDULER_TYPE_CYCLE":周期调度;
     */
    private String schedulerType;

    /**
     * 调度周期
     */
    private String schedulerCycle;
    /**
     * 间隔时间 调度周期为周 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六 7-周日
     */
    private String schedulerIntervalTime;

    /**
     * 生效日期开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTimeStart;

    /**
     * 生效日期结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTimeEnd;

    /**
     * 周期时间
     */
    private String schedulerTime;

    /**
     * 调度执行cron表达式
     */
    private String cron;
    /**
     * 调度定时id
     */
    private Integer schedulerId;

    /**
     * 并发通道
     */
    private Integer currChannel;

    /**
     * 字节流
     */
    private Integer byteStream;

    /**
     * 记录数
     */
    private Integer recordNumber;

    /**
     * 错误记录数
     */
    private Integer errorRecord;

    /**
     * 错误百分比
     */
    private String errorPercent;

    /**
     * 创建人
     */
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;

}
