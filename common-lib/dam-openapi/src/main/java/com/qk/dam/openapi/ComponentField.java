package com.qk.dam.openapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComponentField {
    private String fieldName;
    private String desc;
    private String type;
    private boolean required;
}
