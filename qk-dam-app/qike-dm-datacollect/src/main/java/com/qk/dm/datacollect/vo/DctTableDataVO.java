package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回表名称信息
 * @author zys
 * @date 2022/4/28 16:18
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DctTableDataVO {
  private String dirId;

  private String title;

  private String value;

  private List<DctTableDataVO> children;
}