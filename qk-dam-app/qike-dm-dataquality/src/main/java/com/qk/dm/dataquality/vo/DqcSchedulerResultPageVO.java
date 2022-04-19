package com.qk.dm.dataquality.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据质量_规则结果集入参对象
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerResultPageVO {

    private Pagination pagination;

    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;

    /**
     * 结果集
     */
    private String warnResult;

    /**
     * 分类目录
     */
    private String dirId;
}
