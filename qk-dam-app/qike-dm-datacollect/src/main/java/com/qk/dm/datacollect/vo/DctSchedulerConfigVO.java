package com.qk.dm.datacollect.vo;

import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datacollect.service.cron.CronService;
import com.qk.dm.dolphin.common.enums.SchedulerCycleEnum;
import com.qk.dm.dolphin.common.enums.SchedulerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Objects;

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
public class DctSchedulerConfigVO {

    /**
     * 调度执行方式 "SINGLE":"单次调度" "CYCLE":周期调度;
     */
    @NotBlank(message = "调度执行方式不能为空！")
    private String schedulerType;

    /**
     * 有效日期开始
     */
    private String effectiveTimeStart;

    /**
     * 有效日期结束
     */
    private String effectiveTimeEnt;

    /**
     * 调度周期 分钟 小时 天 周
     */
    private String schedulerCycle;

    /**
     * 间隔时间 调度周期为周 周一 周二 周三 周四 周五 周六 周日
     */
    private String schedulerIntervalTime;

    /**
     * 调度时间
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
     * 生成cron表达式
     *
     * @param dqcSchedulerConfigVO
     * @param cronServiceMap
     * @return
     */
    public void generateCron(DctSchedulerConfigVO dqcSchedulerConfigVO, Map<String, CronService> cronServiceMap) {
        if (Objects.equals(dqcSchedulerConfigVO.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode())) {
            SchedulerCycleEnum schedulerCycleEnum = SchedulerCycleEnum.fromValue(dqcSchedulerConfigVO.getSchedulerCycle());
            CronService cronService = cronServiceMap.get(schedulerCycleEnum.getName());
            dqcSchedulerConfigVO.setCron(cronService.createCronExpression(dqcSchedulerConfigVO));
        }
    }

    public static DctSchedulerConfigVO jsonStringChangeConfig(String value) {
        return BeanMapUtils.changeMapToBean((Map<String, Object>) GsonUtil.fromJsonString(value), new DctSchedulerConfigVO());
    }
}
