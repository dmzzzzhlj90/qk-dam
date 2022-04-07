package com.qk.dm.dataingestion.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisParamsVO {

    @NotNull(message = "分页信息不能为空")
    private Pagination pagination;
    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 规则分类id
     */
    private Long ruleClassId;
}
