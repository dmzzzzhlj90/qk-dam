package com.qk.dm.metadata.vo;

import com.qk.dam.jpa.pojo.Pagination;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzp
 * @date 2021/7/31 11:34
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdClassifyAtlasVO {

  private Pagination pagination;

  private Long id;

  /** 元数据标识 */
  @NotBlank(message = "元数据标识不能为空！")
  private String guid;

  /** 分类 */
  private String classify;
}
