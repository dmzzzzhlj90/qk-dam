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
     * 定时是否开启 0开启 1关闭
     */
    private Integer timeSwitch;

    /**
     * 调度执行方式 "SCHEDULER_TYPE_SINGLE":"单次调度" "SCHEDULER_TYPE_CYCLE":周期调度;
     */
    private String schedulerType;

    /**
     * 调度周期
     */
    private String schedulerCycle;

    /**
     * 生效日期开始
     */
    private Date effectiveTimeStart;

    /**
     * 生效日期结束
     */
    private Date effectiveTimeEnt;

    /**
     * 周期时间
     */
    private String schedulerTime;

    /**
     * 调度执行cron表达式
     */
    private String cron;

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

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
