package com.qk.dam.metadata.catacollect.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zys
 * @date 2022/4/14 20:26
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MetadataConnectInfoVo extends ConnectInfoVo {
  /**
   * 表名称
   */
  private List<String> tableList;

  /**
   * 全选字段
   */
  private String allNums;
}