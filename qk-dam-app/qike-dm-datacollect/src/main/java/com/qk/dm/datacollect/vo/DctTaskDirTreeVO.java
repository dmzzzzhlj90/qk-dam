package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 元数据采集_任务目录树_返回值VO
 *
 * @author zys
 * @date 2022/04/27
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DctTaskDirTreeVO {

    private Long id;

    private String dirId;

    private String title;

    private String value;

    private String parentId;

    private String type;

    private List<DctTaskDirTreeVO> children;

}
