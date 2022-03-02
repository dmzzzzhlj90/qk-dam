package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
public class AtyUserVO {
    String realm;
    @NotNull(message = "启用状态必填!")
    private Boolean enabled;
    private String firstName;
    private String lastName;
    @Email
    private String email;
}
