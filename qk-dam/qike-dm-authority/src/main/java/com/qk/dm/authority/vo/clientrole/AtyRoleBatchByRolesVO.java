package com.qk.dm.authority.vo.clientrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:17
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyRoleBatchByRolesVO {
    /**
     * 域
     */
    @NotBlank(message = "域必填！")
    String realm;
    /**
     * 用户
     */
    @NotBlank(message = "用户id必填！")
    String userId;
    /**
     * 角色
     */
    @NotEmpty(message = "角色列表必填！")
    List<String> roleNames;
    /**
     * 客户端表id
     */
    @NotBlank(message = "客户端id必填!")
    String client_id;
}
