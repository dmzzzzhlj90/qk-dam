package com.qk.dm.datacollect.dolphin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/26 4:17 下午
 * @since 1.0.0
 */
public class ScheduleResultDTO implements Serializable {
    private Long total;
    private Long currentPage;
    private Long totalPage;
    private Long pageSize;
    private Long start;
    private List<ScheduleDTO> totalList;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public List<ScheduleDTO> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<ScheduleDTO> totalList) {
        this.totalList = totalList;
    }
}
