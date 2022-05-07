
package com.qk.dm.dolphin.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流实例_查询结果集
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Data
public class ProcessDefinitionResultDTO implements Serializable {
    private Long total;
    private Long currentPage;
    private Long totalPage;
    private Long pageSize;
    private Long start;
    private List<ProcessDefinitionDTO> totalList;
}