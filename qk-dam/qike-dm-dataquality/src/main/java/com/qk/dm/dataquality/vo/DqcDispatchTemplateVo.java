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
public class DqcDispatchTemplateVo {

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空！")
    private String tempName;

    /**
     * 分类目录
     */
    @NotNull(message = "分类目录不能为空！")
    private Long directoryId;

    /**
     * 提示级别 1-严重
     */
    @NotNull(message = "提示级别不能为空！")
    private Integer notifyLevel;

    /**
     * 通知状态 0-关 1-开
     */
    @NotNull(message = "通知状态不能为空！")
    private Integer notifyState;

    /**
     * 通知类型 1-触发告警 2-运行成功
     */
    @NotNull(message = "通知类型不能为空！")
    private Integer notifyType;

    /**
     * 主题，多个以逗号分隔
     */
    @NotBlank(message = "主题不能为空！")
    private String notifyThemeId;

    /**
     * 调度状态 1-调度中 2-运行中 3-停止
     */
    private Integer dispatchState = 0;
}
