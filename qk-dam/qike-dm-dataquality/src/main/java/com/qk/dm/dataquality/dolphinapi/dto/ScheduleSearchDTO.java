package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenpj
 * @date 2021/11/30 3:59 下午
 * @since 1.0.0
 */
@Data
@Builder
public class ScheduleSearchDTO implements Serializable {
    Integer processDefinitionId;
    Integer pageNo;
    Integer pageSize;
    /** 搜索值 */
    String searchVal;

    public ScheduleSearchDTO() {
    }

    public ScheduleSearchDTO(Integer processDefinitionId, Integer pageNo, Integer pageSize) {
        this.processDefinitionId = processDefinitionId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public ScheduleSearchDTO(Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
        this.processDefinitionId = processDefinitionId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.searchVal = searchVal;
    }
}
