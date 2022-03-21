package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author shenpj
 * @date 2022/3/18 12:39
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserRoleFiltroVO {
    /**
     * 域
     */
    @NotBlank(message = "域必填!")
    String realm;
    /**
     * 客户端表id
     */
    @NotBlank(message = "客户端id必填!")
    String client_id;
    /**
     * 用户名
     */
    String search;
    @NotBlank(message = "用户id必填!")
    String userId;
    @NotBlank(message = "客户端标识必填!")
    String client_clientId;
}
