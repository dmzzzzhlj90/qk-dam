package com.qk.dm.authority.vo.user;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AtyUserCreateVO extends AtyUserVO {
    /**
     * 用户名
     */
    @NotNull(message = "用户名必填!")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码必填!")
    private String password;
}
