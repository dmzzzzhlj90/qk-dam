package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserGroupFiltroVO {
    @NotNull(message = "用户id必填！")
    private String userId;
    @NotNull(message = "域必填！")
    private String realm;
    /**
     * 用户组名称
     */
    String search;
}
