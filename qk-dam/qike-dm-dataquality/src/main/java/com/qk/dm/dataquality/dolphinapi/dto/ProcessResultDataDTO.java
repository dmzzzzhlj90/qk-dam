
package com.qk.dm.dataquality.dolphinapi.dto;

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
public class ProcessResultDataDTO implements Serializable {

    private List<ProcessDefinitionDTO> totalList;

    private int total;

    private int currentPage;

    private int totalPage;

}