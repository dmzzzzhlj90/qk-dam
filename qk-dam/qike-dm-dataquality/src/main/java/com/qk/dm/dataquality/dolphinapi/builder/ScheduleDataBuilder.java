package com.qk.dm.dataquality.dolphinapi.builder;

import lombok.Data;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/16 6:10 下午
 * @since 1.0.0
 */
@Data
public class ScheduleDataBuilder {
  private Integer currentPage;
  private Integer total;
  private Integer totalPage;
  private List<ScheduleData> totalList;
}
