package com.qk.dm.authority.vo.clientrole;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/3/3 14:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyClientRoleVO {
    /**
     * 域
     */
    @NotNull
    String realm;
    /**
     * 角色名称
     */
    @NotNull
    String roleName;
    /**
     * 描述
     */
    String description;
}
