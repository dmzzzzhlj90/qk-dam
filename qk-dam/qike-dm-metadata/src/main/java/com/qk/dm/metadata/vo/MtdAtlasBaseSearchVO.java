package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/8/26 4:59 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdAtlasBaseSearchVO{

    private String query;
    @Builder.Default private String typeName = "_ALL_ENTITY_TYPES";

    private String classification;
    @Builder.Default
    private int limit = 20;
    @Builder.Default
    private int offse = 0;
    List<MtdAtlasSearchVO> list;
}
