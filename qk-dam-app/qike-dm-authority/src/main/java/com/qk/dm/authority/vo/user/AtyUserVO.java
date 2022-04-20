package com.qk.dm.authority.vo.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
public class AtyUserVO {
    /**
     * 域
     */
    @NotBlank(message = "域必填！")
    String realm;
    /**
     * 开启状态
     */
    @NotNull(message = "启用状态必填!")
    private Boolean enabled;
    /**
     * 名字
     */
    private String firstName;
    /**
     * 姓氏
     */
    private String lastName;
    /**
     * 邮箱
     */
    @Email
    private String email;
}
