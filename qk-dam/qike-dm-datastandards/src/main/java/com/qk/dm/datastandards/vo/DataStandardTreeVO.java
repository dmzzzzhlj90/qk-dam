package com.qk.dm.datastandards.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjq
 * @date 20210603
 * @since 1.0.0 数据标准__目录VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStandardTreeVO {

  private Integer id;

  private String dirDsdId;

  private String dirDsdName;

  private String parentId;

  private List<DataStandardTreeVO> children;
}
