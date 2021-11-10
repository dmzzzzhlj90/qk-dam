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
     * 作业名称
     */
    private String taskName;

    /**
     * 分类目录
     */
    private Long dirId;

    /**
     * 提示级别 1-严重
     */
    private Integer notifyLevel;

    /**
     * 通知状态 0-关 1-开
     */
    private Integer notifyState;

    /**
     * 通知类型 1-触发告警 2-运行成功
     */
    private Integer notifyType;

    /**
     * 主题，多个以逗号分隔
     */
    private String notifyThemeId;

    /**
     * 调度状态 1-调度中 2-运行中 3-停止
     */
    private Integer dispatchState;

    /**
     * 创建人
     */
    private Long createUserid;

    /**
     * 修改人
     */
    private Long updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
