package com.qk.dm.indicator.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcBusinessPageDTO {

    private Pagination pagination;

    /**
     * 业务指标名称
     */
    private String bIndicatorName;

    /**
     * 指标负责人
     */
    private String indicatorPersonLiable;

    /**
     * 指标状态 0草稿 1已上线 2已下线
     */
    private Integer indicatorStatus;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

}
