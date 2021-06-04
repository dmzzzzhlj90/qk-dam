package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__码表目录VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStandardCodeTreeVO {

    private Integer id;

    private Integer codeDirId;

    private String codeDirName;

    private Integer parentId;

    private List<DataStandardCodeTreeVO> children;

}
