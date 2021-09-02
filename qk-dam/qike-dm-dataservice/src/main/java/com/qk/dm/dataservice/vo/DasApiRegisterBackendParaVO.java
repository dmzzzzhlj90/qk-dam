package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterBackendParaVO {

    /**
     * 入参名称
     */
    private String paraName;

    /**
     * 入参位置
     */
    private String paraPosition;

    /**
     * 入参类型
     */
    private String paraType;

    /**
     * 后端参数名称
     */
    private String backendParaName;

    /**
     * 后端参数位置
     */
    private String backendParaPosition;

    /**
     * 描述
     */
    private String description;
}
