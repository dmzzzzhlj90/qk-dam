package com.qk.dm.metadata.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果集VO
 *
 * @author wjq
 * @date 2021/6/8
 * @since 1.0.0
 */
@Data
public class PageResultVO<T> {

  /** 数据总量 */
  private long total;

  /** 当前页 */
  private int pageNum;

  /** 每页大小 */
  private int pageSize;

  /** 返回数据data */
  private List<T> list = new ArrayList();

  public PageResultVO(long total, int pageNum, int pageSize, List<T> list) {
    this.total = total;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.list = list;
  }
}
