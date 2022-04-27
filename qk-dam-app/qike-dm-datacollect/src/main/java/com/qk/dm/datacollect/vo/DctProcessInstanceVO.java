package com.qk.dm.datacollect.vo;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datacollect.dolphin.dto.InstanceStateTypeEnum;
import com.qk.dm.datacollect.dolphin.dto.ProcessInstanceDTO;
import com.qk.dm.datacollect.mapstruct.DctProcessInstanceMapper;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author shenpengjie
 */
@Data
public class DctProcessInstanceVO {
    private Long id;
    /**
     * 流程code
     */
    private Long processDefinitionCode;
    /**
     * 状态
     */
    private String state;
    /**
     * 容错标示
     */
    private String recovery;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 运行次数
     */
    private Long runTimes;
    /**
     * 工作流名称
     */
    private String name;
    /**
     * 机器地址
     */
    private String host;
    /**
     * 运行类型
     */
    private String commandType;
    /**
     * 执行用户
     */
    private String executorName;
    /**
     * 运行时长
     */
    private String duration;

    /**
     * 状态名称
     */
    String stateName;

    /**
     * 任务实例id
     */
    private Long taskInstanceId;

    private String dependenceScheduleTimes;

    public void setState(String state) {
        this.state = state;
        this.stateName = Objects.requireNonNull(InstanceStateTypeEnum.fromValue(state)).getValue();
    }

    public static PageResultVO<DctProcessInstanceVO> getInstancePage(Long total, Pagination pagination, List<ProcessInstanceDTO> list) {
        return new PageResultVO<>(
                total,
                pagination.getPage(),
                pagination.getSize(),
                DctProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(list)
        );
    }

    public static void changeName(List<DctProcessInstanceVO> infoList) {
        if (infoList != null) {
            infoList.forEach(info -> {
                if (info.getName().split("_").length > 1) {
                    info.setName(info.getName().split("_")[1]);
                }
            });
        }
    }
}
