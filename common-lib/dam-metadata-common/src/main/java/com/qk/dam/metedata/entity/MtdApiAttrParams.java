package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdApiAttrParams {
    private String typeName;
    private String attrName;
    private String attrValue;
    private Integer limit;
    private Integer offset;
}
