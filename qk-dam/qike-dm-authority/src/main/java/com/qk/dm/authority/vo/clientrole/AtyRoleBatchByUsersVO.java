package com.qk.dm.authority.vo.clientrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class AtyRoleBatchByUsersVO {
    /**
     * 域
     */
    @NotNull
    String realm;
    /**
     * 用户
     */
    @NotBlank
    List<String> userIds;
    /**
     * 角色
     */
    @NotNull
    String roleName;
    /**
     * 客户端的id
     */
    @NotNull
    String client_id;
}
