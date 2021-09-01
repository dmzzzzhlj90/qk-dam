package com.qk.dam.jpa.pojo;

import java.util.List;

/**
 * 分页结果集VO
 *
 * @author wjq
 * @date 2021/6/8
 * @since 1.0.0
 */
public class PageResultVO<O> {

  /** 数据总量 */
  private long total;

  /** 当前页 */
  private int pageNum;

  /** 每页大小 */
  private int pageSize;

  /** 返回数据data */
  private List<O> list;

  public PageResultVO() {}

  public PageResultVO(long total, int pageNum, int pageSize, List<O> list) {
    this.total = total;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.list = list;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public List<O> getList() {
    return list;
  }

  public void setList(List<O> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "PageResultVO{"
        + "total="
        + total
        + ", pageNum="
        + pageNum
        + ", pageSize="
        + pageSize
        + ", list="
        + list
        + '}';
  }
}