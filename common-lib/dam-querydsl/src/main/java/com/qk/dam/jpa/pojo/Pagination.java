package com.qk.dam.jpa.pojo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 列表数据分页VO对象
 *
 * @author wjq
 * @date 2021/6/7
 * @since 1.0.0
 */
public class Pagination {
  /** 分页默认参数设置,当前页1,每页10条,根据ID进行排序 */
  static final int PAGE_DEFAULT_NUM = 1;

  static final int PAGE_DEFAULT_SIZE = 10;
  static final String PAGE_DEFAULT_SORT = "id";

  private int page;
  private int size;
  private String sortField;

  public Pageable getPageable() {
    if (page == 0 && size == 0) {
      return PageRequest.of(
          PAGE_DEFAULT_NUM - 1, PAGE_DEFAULT_SIZE, Sort.by(Sort.Direction.ASC, PAGE_DEFAULT_SORT));
    }

    if (sortField != null && sortField.equals("")) {
      return PageRequest.of(
          this.page - 1, this.size, Sort.by(Sort.Direction.ASC, PAGE_DEFAULT_SORT));
    }
    return PageRequest.of(this.page - 1, this.size, Sort.by(Sort.Direction.ASC, this.sortField));
  }

  public Pagination() {}

  public Pagination(int page, int size, String sortField) {
    this.page = page;
    this.size = size;
    this.sortField = sortField;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  @Override
  public String toString() {
    return "Pagination{"
        + "page="
        + page
        + ", size="
        + size
        + ", sortField='"
        + sortField
        + '\''
        + '}';
  }
}
