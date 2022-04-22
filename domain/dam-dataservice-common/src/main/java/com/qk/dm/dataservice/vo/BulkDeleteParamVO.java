package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量删除
 *
 * @author wjq
 * @date 2022/03/22
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkDeleteParamVO {

    /**
     * id集合
     */
    private List<Long> ids;

}
