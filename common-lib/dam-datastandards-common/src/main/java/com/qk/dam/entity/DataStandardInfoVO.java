package com.qk.dam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主题返回值VO
 * @author zys
 * @date 2022/1/6 15:51
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStandardInfoVO {
  //主键id
  private Integer id;
  //主题id
  private String dirId;
  //主题名称
  private String title;
  //主题父级id
  private String parentId;
  //层级名称
  private String value;
  //主题子级
  private List<DataStandardInfoVO> children;
}