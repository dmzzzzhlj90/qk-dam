package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 趋势曲线值对象VO
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasTrendCurveValVO {

    /**
     * X横轴
     */
    private String xVal;

    /**
     * y横轴
     */
    private Integer yVal;


}
