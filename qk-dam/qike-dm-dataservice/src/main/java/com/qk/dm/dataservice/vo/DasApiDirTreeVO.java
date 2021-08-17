package com.qk.dm.dataservice.vo;

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
public class DasApiDirTreeVO {

  private Integer id;

  private String apiDirId;

  private String apiDirName;

  private String parentId;

  private String apiDirLevel;

  private List<DasApiDirTreeVO> children;
}
