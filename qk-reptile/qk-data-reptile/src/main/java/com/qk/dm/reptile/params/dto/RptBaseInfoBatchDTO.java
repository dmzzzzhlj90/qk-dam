package com.qk.dm.reptile.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量添加
 * @author wangzp
 * @date 2021/03/07 14:28
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RptBaseInfoBatchDTO extends RptBaseInfoDTO{

    @NotNull(message = "列表页地址不能为空")
    private List<String> listPageAddressList;
}
