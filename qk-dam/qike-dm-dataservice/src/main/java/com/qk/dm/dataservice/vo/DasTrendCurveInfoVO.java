package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 趋势曲线信息VO
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasTrendCurveInfoVO {

    /**
     * y横轴
     */
    private List<DasTrendCurveValVO> dasTrendCurveValVOS;
}
