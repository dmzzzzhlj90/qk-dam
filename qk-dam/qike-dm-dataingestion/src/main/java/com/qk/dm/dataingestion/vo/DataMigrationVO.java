package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataMigrationVO {
    /**
     * 作业基本信息
     */
    @NotNull(message = "作业基础信息不能为空")
    private DisMigrationBaseInfoVO baseInfo;
    /**
     * 字段信息
     */
    @NotNull(message = "字段信息不能为空")
    private List<DisColumnInfoVO> columnList;
    /**
     * 任务配置信息
     */
    @NotNull(message = "任务配置不能为空")
    private DisSchedulerConfigVO schedulerConfig;
}
