package com.qk.dm.dataingestion.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据迁移调度配置
 */
@Data
@Entity
@Table(name = "qk_dis_scheduler_config")
public class DisSchedulerConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 迁移基础表id
     */
    @Column(name = "base_info_id", nullable = false)
    private Long baseInfoId;

    /**
     * 定时是否开启 0开启 1关闭
     */
    @Column(name = "time_switch")
    private Boolean timeSwitch;

    /**
     * 调度执行方式 "SCHEDULER_TYPE_SINGLE":"单次调度" "SCHEDULER_TYPE_CYCLE":周期调度;
     */
    @Column(name = "scheduler_type")
    private String schedulerType;

    /**
     * 调度周期
     */
    @Column(name = "scheduler_cycle")
    private String schedulerCycle;

    /**
     * 间隔时间 调度周期为周 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六 7-周日
     */
    @Column(name = "scheduler_interval_time")
    private String schedulerIntervalTime;

    /**
     * 生效日期开始
     */
    @Column(name = "effective_time_start")
    private Date effectiveTimeStart;

    /**
     * 生效日期结束
     */
    @Column(name = "effective_time_end")
    private Date effectiveTimeEnd;

    /**
     * 周期时间
     */
    @Column(name = "scheduler_time")
    private String schedulerTime;

    /**
     * 调度执行cron表达式
     */
    @Column(name = "cron")
    private String cron;
    /**
     * 调度定时id
     */
    @Column(name = "scheduler_id")
    private Integer schedulerId;

    /**
     * 并发通道
     */
    @Column(name = "curr_channel")
    private Integer currChannel;

    /**
     * 字节流
     */
    @Column(name = "byte_stream")
    private Integer byteStream;

    /**
     * 记录数
     */
    @Column(name = "record_number")
    private Integer recordNumber;

    /**
     * 错误记录数
     */
    @Column(name = "error_record")
    private Integer errorRecord;

    /**
     * 错误百分比
     */
    @Column(name = "error_percent")
    private String errorPercent;

    /**
     * 创建人
     */
    @Column(name = "create_userid")
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

}
