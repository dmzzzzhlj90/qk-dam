package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_dispatch")
public class DqcDispatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 调度模版id
     */
    @Column(name = "dispatch_temp_id", nullable = false)
    private Long dispatchTempId;

    /**
     * 调度执行方式 1-手动执行 2-调度执行
     */
    @Column(name = "run_type", nullable = false)
    private String runType;

    /**
     * 调度周期 年、月、周、日
     */
    @Column(name = "dispatch_cycle")
    private String dispatchCycle;

    /**
     * 周期开始时间
     */
    @Column(name = "dispatch_time_start")
    private Date dispatchTimeStart;

    /**
     * 周期结束时间
     */
    @Column(name = "dispatch_time_ent")
    private Date dispatchTimeEnt;

    /**
     * 间隔时间
     */
    @Column(name = "dispatch_interval_time")
    private String dispatchIntervalTime;

    /**
     * 调度执行cron表达式
     */
    @Column(name = "cron")
    private String cron;

    /**
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
    private Long createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private Long updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false)
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

}
