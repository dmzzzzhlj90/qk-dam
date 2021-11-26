
package com.qk.dm.dataquality.dolphinapi.dto;
import lombok.Data;

import java.util.List;

/**
 * 流程实例节点_条件结果定义
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class ConditionResultDTO {
    private List<String> successNode;
    private List<String> failedNode;

}