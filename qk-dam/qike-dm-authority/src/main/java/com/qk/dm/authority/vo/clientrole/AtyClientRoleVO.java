package com.qk.dm.authority.vo.clientrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "域必填!")
    String realm;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称必填!")
    String roleName;
    /**
     * 客户端表id
     */
    @NotBlank(message = "客户端id必填!")
    String client_id;
    /**
     * 描述
     */
    String description;
}
