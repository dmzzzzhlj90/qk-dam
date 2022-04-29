package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class disAttrVO {
    private Map<String, List<DisAttrViewVO>> source;
    private Map<String,List<DisAttrViewVO>> target;
}
