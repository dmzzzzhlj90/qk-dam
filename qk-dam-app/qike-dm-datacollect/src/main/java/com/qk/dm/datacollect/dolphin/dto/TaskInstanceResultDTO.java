package com.qk.dm.datacollect.dolphin.dto;

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
public class TaskInstanceResultDTO implements Serializable {
    private Long total;
    private Long currentPage;
    private Long totalPage;
    private Long pageSize;
    private Long start;
    private List<TaskInstanceDTO> totalList;
}
