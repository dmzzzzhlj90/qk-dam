package com.qk.dm.authority.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "用户名必填!")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码必填!")
    private String password;
}
