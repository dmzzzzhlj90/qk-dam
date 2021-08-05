package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
  private int page;
  private int size;
  private String sortStr;

  public Pageable getPageable() {
    if (page == 0 && size == 0) {
      return PageRequest.of(
          1 - 1,
          10,
          Sort.by(Sort.Direction.ASC, "id"));
    }

    if (StringUtils.isEmpty(sortStr)) {
      return PageRequest.of(
          this.page - 1, this.size, Sort.by(Sort.Direction.ASC, "id"));
    }
    return PageRequest.of(this.page - 1, this.size, Sort.by(Sort.Direction.ASC, this.sortStr));
  }
}
