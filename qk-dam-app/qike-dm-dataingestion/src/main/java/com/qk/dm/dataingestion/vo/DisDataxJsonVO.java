package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * datax json
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisDataxJsonVO {
    /**
     * 作业基础表id
     */
    private Long baseInfoId;


    /**
     * datax json数据
     */
    private String dataxJson;
}
