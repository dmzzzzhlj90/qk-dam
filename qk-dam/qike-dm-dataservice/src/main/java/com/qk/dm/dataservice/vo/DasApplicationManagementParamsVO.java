package com.qk.dm.dataservice.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2021/8/17 17:46
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApplicationManagementParamsVO {
    private Pagination pagination;
    /** 应用名称 */
    private String appName;
    /** 应用类型 */
    private String appType;
    /** 开始时间 */
    private String beginDay;

    /** 结束时间 */
    private String endDay;
    //用于授权
    /**
     * 应用id
     */
    private Long id;

    /**
     * 关联api的id
     */
    private String apiIds;
}