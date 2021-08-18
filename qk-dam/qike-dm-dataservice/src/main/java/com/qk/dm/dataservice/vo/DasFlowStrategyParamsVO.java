package com.qk.dm.dataservice.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2021/8/18 12:07
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasFlowStrategyParamsVO {
    private Pagination pagination;
    /** 开始时间 */
    private String beginDay;

    /** 结束时间 */
    private String endDay;
    /** 策略名称 */
    private String strategyName;
}