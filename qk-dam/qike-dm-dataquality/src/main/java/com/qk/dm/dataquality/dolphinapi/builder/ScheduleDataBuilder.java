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
    private List<ScheduleList> totalList;

    @Data
    public class ScheduleList{
        private Integer id;
        private String createTime;
        private String crontab;
        private String definitionDescription;
        private String endTime;
        private String failureStrategy;
        private Integer processDefinitionId;
        private String processDefinitionName;
        private String processInstancePriority;
        private String projectName;
        private String releaseState;
        private String startTime;
        private String updateTime;
        private Integer userId;
        private String userName;
        private Integer warningGroupId;
        private String warningType;
        private String workerGroup;
    }
}
