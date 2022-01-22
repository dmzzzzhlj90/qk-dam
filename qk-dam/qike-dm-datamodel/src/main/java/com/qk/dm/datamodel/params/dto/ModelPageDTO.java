package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ModelPageDTO {

    @NotNull(message = "分页参数不能为空")
    private Pagination pagination;
}
