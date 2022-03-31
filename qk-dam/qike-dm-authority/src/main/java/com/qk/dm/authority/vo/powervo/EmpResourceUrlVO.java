package com.qk.dm.authority.vo.powervo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 根据用户id查询资源VO
 * @author zys
 * @date 2022/3/30 17:27
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpResourceUrlVO {
  /**
   * 主键id
   */
  private Long id;

  /**
   * 父级id（API类型fid默认为-1）
   */
  private Long pid;

  /**
   * 资源（API）名称
   */
  private String name;

  /**
   * 网址路径
   */
  private String path;

  /**
   * 子节点集合
   */
  private List<EmpResourceUrlVO> chirdren;
}