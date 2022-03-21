package com.qk.dm.dataservice.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API应用管理__查询条件VO
 *
 * @author wjq
 * @date 2022/03/21
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiAppManageInfoParamsVO {

    private Pagination pagination;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * API类型
     */
    private String apiType;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;

}
