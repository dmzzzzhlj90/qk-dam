package com.qk.dm.authority.vo.group;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询用户入参
 * @author zys
 * @date 2022/2/23 15:56
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyGroupParamVO {
  /**
   * 域
   */
  String realm;
  /**
   * 分页信息
   */
  private Pagination pagination;
  /**
   * 用户查询条件
   */
  private String search;
}