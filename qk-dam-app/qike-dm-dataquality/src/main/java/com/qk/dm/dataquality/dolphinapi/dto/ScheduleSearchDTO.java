package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shenpj
 * @date 2021/11/30 3:59 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSearchDTO implements Serializable {
    Long processDefinitionCode;
    Integer pageNo;
    Integer pageSize;
    /** 搜索值 */
    String searchVal;
}
