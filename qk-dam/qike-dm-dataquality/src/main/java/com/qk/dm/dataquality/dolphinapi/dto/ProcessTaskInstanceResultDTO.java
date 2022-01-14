package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/26 2:58 下午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessTaskInstanceResultDTO implements Serializable {
    private Integer total;
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;
    private Integer start;
    private List<TaskInstanceDTO> totalList;
}
