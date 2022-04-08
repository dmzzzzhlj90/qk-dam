package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ModelPageDTO {

    private Pagination pagination;
}
