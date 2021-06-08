package com.qk.dm.datastandards.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
        Sort sort = Sort.by(Sort.Direction.ASC, this.sortStr);
        return PageRequest.of(this.page - 1, this.size, sort);
    }
}
