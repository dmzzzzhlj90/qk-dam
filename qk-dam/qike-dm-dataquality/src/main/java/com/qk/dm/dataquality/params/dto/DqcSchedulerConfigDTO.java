package com.qk.dm.dataquality.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class DqcSchedulerConfigDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 作业id
     */
    @NotBlank(message = "作业id不能为空！")
    private String jobId;

    /**
     * 调度执行方式 "SCHEDULER_TYPE_SINGLE":"单次调度" "SCHEDULER_TYPE_CYCLE":周期调度;
     */
    @NotNull(message = "调度执行方式不能为空！")
    private String schedulerType;

    /**
     * 有效日期开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTimeStart;

    /**
     * 有效日期结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTimeEnt;

    /**
     * 调度周期 1-分钟 2-小时 3-天 4-周
     */
    private String schedulerCycle;

    /**
     * 间隔时间 调度周期为周 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六 7-周日
     */
    private String schedulerIntervalTime;

    /**
     * 调度时间
     */
    private String schedulerTime;
}
