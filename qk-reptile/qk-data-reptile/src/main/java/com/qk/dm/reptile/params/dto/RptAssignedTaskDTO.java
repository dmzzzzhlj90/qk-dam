package com.qk.dm.reptile.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分配任务
 */
@Data
public class RptAssignedTaskDTO {
    /**
     * 多个id使用英文逗号分割
     */
    @NotNull(message = "id不能为空")
    private String ids;
    /**
     * 负责人
     */
    @NotNull(message = "负责人不能为空")
    private String responsiblePersonName;
}
