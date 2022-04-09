package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据服务_流控绑定请求参数信息VO
 *
 * @author wjq
 * @date 2022/3/16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiLimitBindParamsVO {

    /**
     * 流控策略ID
     */
    @NotNull(message = "流控策略ID不能为空！")
    private Long limitId;

    /**
     * API路由组ID集合
     */
    @NotNull(message = "API路由组ID集合不能为空！")
    private List<String> routeIds;

}
