package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 趋势饼状图信息VO
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasTrendPieChartInfoVO {

    /**
     * API总计
     */
    private Integer apiSumCount;

    /**
     * 新建未上传数据网关数量
     */
    private Integer createNoUploadCount;

    /**
     * 上传数据网关失败
     */
    private Integer createFailUploadCount;

    /**
     * 成功上传数据网关
     */
    private Integer createSuccessUploadCount;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;
}
