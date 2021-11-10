package com.qk.dm.dataquality.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据质量_规则调度查询入参对象
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdSchedulerAllParamsVO {

    private Pagination pagination;

    /**
     * 作业名称
     */
    private String taskName;

    /**
     * 分类目录
     */
    private Long dirId;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;
}
