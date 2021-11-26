package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/26 4:17 下午
 * @since 1.0.0
 */
@Data
public class ScheduleResultDTO implements Serializable {
    private int total;
    private int currentPage;
    private int totalPage;
    private List<ScheduleDTO> totalList;
}
