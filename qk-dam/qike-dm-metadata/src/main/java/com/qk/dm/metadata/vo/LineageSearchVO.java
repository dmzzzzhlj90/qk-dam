package com.qk.dm.metadata.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhudaoming
 */
@Data
public class LineageSearchVO {
    @NotNull
    private Pagination pagination;
    private String typeName;
    private String qualifiedName;

}
