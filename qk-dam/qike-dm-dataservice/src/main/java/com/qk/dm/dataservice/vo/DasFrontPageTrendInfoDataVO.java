package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页__趋势查询信息VO
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasFrontPageTrendInfoDataVO {

    /**
     * 曲线信息
     */
    private DasTrendCurveInfoVO dasTrendCurveInfoVO;

    /**
     * 饼状图信息
     */
    private DasTrendPieChartInfoVO dasTrendPieChartInfoVO;

}
