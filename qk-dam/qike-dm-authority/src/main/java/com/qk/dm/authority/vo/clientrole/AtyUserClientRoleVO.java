package com.qk.dm.authority.vo.clientrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/3/3 14:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserClientRoleVO {
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
     * 客户端id
     */
    @NotNull
    String client_id;
    /**
     * 用户id
     */
    @NotNull
    String userId;
}
