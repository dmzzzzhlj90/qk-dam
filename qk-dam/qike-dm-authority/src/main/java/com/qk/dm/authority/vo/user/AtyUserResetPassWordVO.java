package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author shenpj
 * @date 2022/3/2 14:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserResetPassWordVO {
    @NotBlank(message = "域必填！")
    private String realm;
    @NotBlank(message = "密码必填！")
    private String password;
}
