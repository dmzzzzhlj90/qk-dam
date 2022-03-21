package com.qk.dm.dataservice.dto;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册API_批量操作转换DTO
 *
 * @author wjq
 * @date 20220303
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRegisterDTO {
    /**
     * API基础信息
     */
    private DasApiBasicInfo dasApiBasicInfo;

    /**
     * 注册API信息
     */
    private DasApiRegister dasApiRegister;

}
