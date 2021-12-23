package com.qk.dm.dataquality.vo.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author shenpj
 * @date 2021/12/23 11:39 上午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDirVO {
    String type;
    BigDecimal value;
}
