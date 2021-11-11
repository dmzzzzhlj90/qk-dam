package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 数据质量_规则调度_配置信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerConfigVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 作业id
     */
    private String taskId;

    /**
     * 调度执行方式 1-手动执行 2-调度执行
     */
    private String runType;

    /**
     * 调度周期 年、月、周、日
     */
    private String schedulerCycle;

    /**
     * 周期开始时间
     */
    private Date schedulerTimeStart;

    /**
     * 周期结束时间
     */
    private Date schedulerTimeEnt;

    /**
     * 间隔时间
     */
    private String schedulerIntervalTime;

    /**
     * 调度执行cron表达式
     */
    private String cron;

    /**
     * 创建人
     */
    private Long createUserid;

    /**
     * 修改人
     */
    private Long updateUserid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 删除标识(0-保留 1-删除)
     */
    private Integer delFlag;

}
