package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wjq
 * @date 20210603
 * 数据标准目录VO
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStandardTreeResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer dirDsdid;

    private String dirDsdName;

    private Integer parentId;

    private List<DataStandardTreeResp> children;

}
