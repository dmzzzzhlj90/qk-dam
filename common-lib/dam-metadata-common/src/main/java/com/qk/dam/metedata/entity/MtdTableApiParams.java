package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdTableApiParams {

    private String typeName;
    private String classification;
}
