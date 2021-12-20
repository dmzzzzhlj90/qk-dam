package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 数据质量_规则调度_基础信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerBasicInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 作业id
     */
    private String jobId;

    /**
     * 作业名称
     */
    @NotBlank(message = "作业名称不能为空！")
    private String jobName;

    /**
     * 分类目录
     */
    @NotNull(message = "分类目录不能为空！")
    private String dirId;

    /**
     * 调度流程实例ID
     */
    @NotNull(message = "调度流程实例ID不能为空！")
    private Long processDefinitionId;

    /**
     * 提示级别 "HINT":"提示","GENERAL":"一般","SERIOUS":"严重","FATAL":"致命";
     */
    @NotNull(message = "提示级别不能为空！")
    private String notifyLevel;

    /**
     * 通知状态 "CLOSE":"关","OPEN":"开";
     */
    @NotNull(message = "通知状态不能为空！")
    private Boolean notifyState;

    /**
     * 通知类型 "TRIGGER_ALARM":"触发告警", "RUN_SUCCESS":"运行成功";
     */
    @NotNull(message = "通知类型不能为空！")
    private String notifyType;

    /**
     * 主题，多个以逗号分隔
     */
    @NotBlank(message = "主题不能为空！")
    private List<String> notifyThemeIdList;

    /**
     * 调度状态 "OFFLINE":"下线","ONLINE":"上线"
     */
    private String schedulerState;

    /**
     * 运行实例状态 0-初始状态 1-运行中 2-停止 3-成功 4-失败
     */
    private Integer runInstanceState;

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

    /**
     * 数据质量_规则调度_规则信息VO
     */
    private List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList;

    /**
     * 数据质量_规则调度_配置信息
     */
    private DqcSchedulerConfigVO dqcSchedulerConfigVO;
}
