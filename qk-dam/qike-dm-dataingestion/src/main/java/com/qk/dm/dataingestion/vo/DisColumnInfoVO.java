package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisColumnInfoVO {


    private Long id;

    /**
     * 迁移基础表id
     */
    private Long baseInfoId;

    /**
     * 字段源名称
     */
    private String sourceName;

    /**
     * 字段源类型
     */
    private String sourceType;

    /**
     * 字段目标名称
     */
    private String targetName;

    /**
     * 字段目标类型
     */
    private String targetType;

    /**
     * 创建人
     */
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;


}
