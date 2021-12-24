package com.qk.dm.dataquality.vo.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2021/12/23 11:34 上午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobInfoVO {
    Long count;
    Integer tableCount;
    Integer fieldCount;
}
