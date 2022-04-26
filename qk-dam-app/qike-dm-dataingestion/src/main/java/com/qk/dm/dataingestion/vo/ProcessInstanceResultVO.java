package com.qk.dm.dataingestion.vo;

import lombok.Data;
import java.util.List;

@Data
public class ProcessInstanceResultVO {
    private Long total;
    private Long currentPage;
    private Long totalPage;
    private Long pageSize;
    private Long start;
    private List<ProcessInstanceVO> totalList;
}