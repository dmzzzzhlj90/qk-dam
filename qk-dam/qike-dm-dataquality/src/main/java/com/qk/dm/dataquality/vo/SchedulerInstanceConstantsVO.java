package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author shenpj
 * @date 2021/12/6 11:17 上午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulerInstanceConstantsVO {

    /**
     * 实例状态
     */
    Map<String,String> instanceStateTypeEnum;

    /**
     * 执行类型
     */
    Map<String,String> executeTypeEnum;
}
